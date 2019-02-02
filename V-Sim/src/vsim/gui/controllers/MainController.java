/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package vsim.gui.controllers;

import java.io.IOException;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.ScrollEvent;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTabPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;
import vsim.Settings;
import vsim.gui.components.InputDialog;
import vsim.gui.utils.ConsoleInput;
import vsim.gui.utils.ConsoleOutput;
import vsim.gui.utils.Icons;
import vsim.simulator.Status;
import vsim.utils.IO;


/** Main controller class. */
public class MainController {

  /** Main tab pane */
  @FXML protected JFXTabPane main;
  /** Main tab pane editor tab */
  @FXML protected Tab editorTab;
  /** Main tab pane simulator tab */
  @FXML protected Tab simTab;
  /** Console tab */
  @FXML protected Tab consoleTab;

  /** Simulator progress */
  @FXML protected JFXProgressBar progress;

  /** Reference to editor controller */
  @FXML protected EditorController editorController;
  /** Reference to menubar controller */
  @FXML protected MenuBarController menuBarController;
  /** Reference to simulator controller */
  @FXML protected SimulatorController simulatorController;

  /** Primary stage */
  protected Stage stage;

  /**
   * Initialize main controller class and other controllers.
   *
   * @param stage primary stage
   */
  public void initialize(Stage stage) {
    this.loading(false);
    this.stage = stage;
    this.initConsole();
    // disable simulation tab if project is not ready
    this.simTab.disableProperty().bind(Bindings.not(Status.READY));
    // set ready to false whenever the tab being selected is the editor tab
    this.main.getSelectionModel().selectedItemProperty().addListener((e, oldTab, newTab) -> {
      if (newTab == this.editorTab)
        Status.READY.set(false);
    });
    this.editorController.initialize(this);
    this.menuBarController.initialize(this);
    this.simulatorController.initialize(this);
  }

  /** Selects editor tab. */
  protected void selectEditorTab() {
    this.main.getSelectionModel().select(this.editorTab);
  }

  /** Selects simulator tab. */
  protected void selectSimulatorTab() {
    this.main.getSelectionModel().select(this.simTab);
  }

  /**
   * Handles progress bar state.
   *
   * @param isLoading if currently in loading mode
   */
  protected void loading(boolean isLoading) {
    Platform.runLater(() -> {
      if (isLoading)
        this.progress.setProgress(-1.0f);
      else
        this.progress.setProgress(0.0f);
    });
  }

  /** Initialize V-Sim console text area. */
  private void initConsole() {
    InlineCssTextArea console = new InlineCssTextArea();
    console.setId("console");
    // assign the new gui "standard" input, output and err
    IO.guistdin = new ConsoleInput(console);
    IO.guistdout = new ConsoleOutput(console);
    IO.guistderr = IO.guistdout;
    try {
      // input dialog
      IO.dialog = new InputDialog();
    } catch (IOException e) {
    }
    // clear option
    MenuItem clear = new MenuItem("clear");
    clear.setOnAction(e -> console.replaceText(0, console.getLength(), ""));
    clear.setGraphic(Icons.getImage("clear"));
    // copy option
    MenuItem copy = new MenuItem("copy");
    copy.setOnAction(e -> console.copy());
    copy.setGraphic(Icons.getImage("copy"));
    // select all option
    MenuItem selectAll = new MenuItem("select all");
    selectAll.setOnAction(e -> console.selectAll());
    selectAll.setGraphic(Icons.getImage("select"));
    // set context menu with this options
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(clear, copy, selectAll);
    console.setContextMenu(menu);
    console.addEventFilter(ScrollEvent.SCROLL, e -> {
      if (e.isControlDown()) {
        if (e.getDeltaY() > 0)
          Settings.incConsoleFontSize();
        else if (e.getDeltaY() < 0)
          Settings.decConsoleFontSize();
        console.setStyle(String.format("-fx-font-size: %dpt;", Settings.CONSOLE_FONT_SIZE));
        e.consume();
      }
    });
    console.setStyle(String.format("-fx-font-size: %dpt;", Settings.CONSOLE_FONT_SIZE));
    this.consoleTab.setContent(new VirtualizedScrollPane<>(console));
  }
}
