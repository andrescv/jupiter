package vsim.gui.controllers;

import vsim.Globals;
import vsim.Settings;
import vsim.utils.IO;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.PrintStream;
import vsim.gui.utils.Icons;
import vsim.simulator.Status;
import javafx.scene.control.Tab;
import vsim.gui.utils.ConsoleInput;
import javafx.application.Platform;
import javafx.scene.control.Tooltip;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.beans.binding.Bindings;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.ContextMenu;
import vsim.gui.utils.CustomOutputStream;
import com.jfoenix.controls.JFXProgressBar;


/**
 * Main controller class.
 */
public class MainController {

  /** Main tab pane */
  @FXML protected JFXTabPane main;
  /** Main tab pane editor tab */
  @FXML protected Tab editorTab;
  /** Main tab pane simulator tab */
  @FXML protected Tab simTab;

  /** Simulator progress */
  @FXML protected JFXProgressBar progress;

  /** Console text area */
  @FXML protected TextArea console;

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

  /**
   * Selects editor tab.
   */
  protected void selectEditorTab() {
    this.main.getSelectionModel().select(this.editorTab);
  }

  /**
   * Selects simulator tab.
   */
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

  /**
   * Initialize V-Sim console text area.
   */
  private void initConsole() {
    // assign the new gui "standard input"
    IO.guistdin = new ConsoleInput(this.console);
    // redirect stdout and stderr
    IO.stdout = new PrintStream(new CustomOutputStream(this.console));
    IO.stderr = IO.stdout;
    // clear option
    MenuItem clear = new MenuItem("clear");
    clear.setOnAction(e -> this.console.setText(""));
    clear.setGraphic(Icons.getImage("clear"));
    // copy option
    MenuItem copy = new MenuItem("copy");
    copy.setOnAction(e -> this.console.copy());
    copy.setGraphic(Icons.getImage("copy"));
    // select all option
    MenuItem selectAll = new MenuItem("select all");
    selectAll.setOnAction(e -> this.console.selectAll());
    selectAll.setGraphic(Icons.getImage("select"));
    // set context menu with this options
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(clear, copy, selectAll);
    this.console.setContextMenu(menu);
  }

}
