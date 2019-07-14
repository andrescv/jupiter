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

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTabPane;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.InlineCssTextArea;

import vsim.gui.Icons;
import vsim.gui.Settings;
import vsim.gui.Status;
import vsim.gui.dialogs.*;
import vsim.utils.IO;
import vsim.utils.io.GUIConsoleInput;
import vsim.utils.io.GUIConsoleOutput;


/** V-Sim GUI main controller. */
public final class Main implements Initializable {

  /** main stage */
  private Stage stage;

  /** if assembling */
  private SimpleBooleanProperty assembling;

  /** about dialog */
  private AboutDialog aboutDialog;
  /** close dialog */
  private CloseDialog closeDialog;
  /** delete dialog */
  private DeleteDialog deleteDialog;
  /** directory chooser */
  private DirectoryDialog directoryDialog;
  /** file chooser */
  private FileDialog fileDialog;
  /** path dialog */
  private PathDialog pathDialog;
  /** save dialog */
  private SaveDialog saveDialog;

  /** snackbar */
  private JFXSnackbar snackbar;

  /** editor controller */
  @FXML protected Editor editorController;
  /** simulator controller */
  @FXML private Simulator simulatorController;

  /** root stack pane */
  @FXML private StackPane rootStackPane;

  /** preloader */
  @FXML private JFXProgressBar preloader;

  /** main tab pane */
  @FXML private JFXTabPane mainTabPane;

  /** editor tab */
  @FXML private Tab editorTab;
  /** simulator tab */
  @FXML private Tab simulatorTab;
  /** console tab */
  @FXML private Tab consoleTab;

  /** show symbol table setting check box */
  @FXML private JFXCheckBox showSymbolTable;
  /** assemble only open files setting check box */
  @FXML private JFXCheckBox assembleOnly;
  /** assemble all files in directory setting check box */
  @FXML private JFXCheckBox assembleAll;
  /** assembler warnings are consider errors setting check box */
  @FXML private JFXCheckBox assemblerWarnings;
  /** permit pseudoinstructions setting check box */
  @FXML private JFXCheckBox permitPseudos;
  /** self-modifying code setting check box */
  @FXML private JFXCheckBox selfModifyingCode;
  /** editor auto indent setting check box */
  @FXML private JFXCheckBox autoIndent;
  /** editor dark mode setting check box */
  @FXML private JFXCheckBox darkMode;
  /** editor tab size 2 setting radio button */
  @FXML private JFXRadioButton tabSize2;
  /** editor tab size 4 setting radio button */
  @FXML private JFXRadioButton tabSize4;
  /** editor tab size 8 setting radio button */
  @FXML private JFXRadioButton tabSize8;

  /** new file menu item */
  @FXML private MenuItem newFile;
  /** open file menu item */
  @FXML private MenuItem openFile;
  /** change project folder menu item */
  @FXML private MenuItem changeProjectFolder;
  /** save menu item */
  @FXML private MenuItem save;
  /** save as menu item */
  @FXML private MenuItem saveAs;
  /** save all menu item */
  @FXML private MenuItem saveAll;
  /** close menu item */
  @FXML private MenuItem close;
  /** close all menu item */
  @FXML private MenuItem closeAll;
  /** undo menu item */
  @FXML private MenuItem undo;
  /** redo menu item */
  @FXML private MenuItem redo;
  /** cut menu item */
  @FXML private MenuItem cut;
  /** copy menu item */
  @FXML private MenuItem copy;
  /** paste menu item */
  @FXML private MenuItem paste;
  /** select all menu item */
  @FXML private MenuItem selectAll;
  /** find and replace menu item */
  @FXML private MenuItem findAndReplace;
  /** assemble menu item */
  @FXML private MenuItem assemble;
  /** run menu item */
  @FXML private MenuItem run;
  /** step menu item */
  @FXML private MenuItem step;
  /** backstep menu item */
  @FXML private MenuItem backstep;
  /** stop menu item */
  @FXML private MenuItem stop;
  /** reset menu item */
  @FXML private MenuItem reset;
  /** clear all breakpoints menu item */
  @FXML private MenuItem clearAllBreakpoints;


  /** {@inheritDoc} */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    // set loading state
    loading(false);
    // create assembling property
    assembling = new SimpleBooleanProperty(false);
    // init other controllers
    editorController.initialize(this);
    simulatorController.initialize(this);
    // init controls
    initControls();
  }

  /**
   * Sets controller stage.
   *
   * @param stage main stage
   */
  public void setStage(Stage stage) {
    // save primary stage
    this.stage = stage;
    stage.setOnCloseRequest(e -> {
      e.consume();
      editorController.quit();
    });
  }

  /** Closes main stage. */
  protected void closeStage() {
    stage.close();
  }

  /**
   * Sets preloader state.
   *
   * @param active if loading or not
   */
  protected void loading(boolean active) {
    Platform.runLater(() -> {
      preloader.setProgress(active ? -1.0f : 0.0f);
    });
  }

  /** Selects editor tab. */
  protected void editor() {
    mainTabPane.getSelectionModel().select(editorTab);
  }

  /** Selects simulator tab. */
  protected void simulator() {
    mainTabPane.getSelectionModel().select(simulatorTab);
  }

  /**
   * Sets assembling state.
   *
   * @param state new state
   */
  protected void assembling(boolean state) {
    assembling.set(state);
  }

  /**
   * Returns V-Sim about dialog.
   *
   * @return about dialog
   */
  protected AboutDialog aboutDialog() {
    if (aboutDialog == null) {
      aboutDialog = new AboutDialog(rootStackPane);
    }
    return aboutDialog;
  }

  /**
   * Returns V-Sim close dialog.
   *
   * @return close dialog
   */
  protected CloseDialog closeDialog() {
    if (closeDialog == null) {
      closeDialog = new CloseDialog(stage);
    }
    return closeDialog;
  }

  /**
   * Returns V-Sim delete dialog.
   *
   * @return delete dialog
   */
  protected DeleteDialog deleteDialog() {
    if (deleteDialog == null) {
      deleteDialog = new DeleteDialog(stage);
    }
    return deleteDialog;
  }

  /**
   * Returns V-Sim directory chooser dialog.
   *
   * @return directory chooser dialog
   */
  protected DirectoryDialog directoryDialog() {
    if (directoryDialog == null) {
      directoryDialog = new DirectoryDialog(stage);
    }
    return directoryDialog;
  }

  /**
   * Returns V-Sim file chooser dialog.
   *
   * @return file chooser dialog
   */
  protected FileDialog fileDialog() {
    if (fileDialog == null) {
      fileDialog = new FileDialog(stage);
    }
    return fileDialog;
  }

  /**
   * Returns V-Sim path dialog.
   *
   * @return path dialog
   */
  protected PathDialog pathDialog() {
    if (pathDialog == null) {
      pathDialog = new PathDialog(stage);
    }
    return pathDialog;
  }

  /**
   * Returns V-Sim save dialog.
   *
   * @return save dialog
   */
  protected SaveDialog saveDialog() {
    if (saveDialog == null) {
      saveDialog = new SaveDialog(stage);
    }
    return saveDialog;
  }

  /**
   * Creates a toast message.
   *
   * @param msg toast message
   * @param duration toast duration
   */
  protected void toast(String msg, long duration) {
    if (snackbar == null) {
      snackbar = new JFXSnackbar(rootStackPane);
    }
    snackbar.enqueue(new JFXSnackbar.SnackbarEvent(new Label(msg), new Duration(duration), null));
  }

  /*===================*
   |  MENUBAR ACTIONS  |
   *===================*/

  /** Calls editor {@code newFile} method. */
  @FXML private void newFile() {
    editorController.newFile();
  }

  /** Calls editor {@code openFile} method. */
  @FXML private void openFile() {
    editorController.openFile();
  }

  /** */
  @FXML private void changeProjectFolder() {
    editorController.changeProjectFolder();
  }

  /** Calls editor {@code save} method. */
  @FXML private void save() {
    editorController.save();
  }

  /** Calls editor {@code saveAs} method. */
  @FXML private void saveAs() {
    editorController.saveAs();
  }

  /** Calls editor {@code saveAll} method. */
  @FXML private void saveAll() {
    editorController.saveAll();
  }

  /** Calls editor {@code close} method. */
  @FXML private void close() {
    editorController.close();
  }

  /** Calls editor {@code closeAll} method. */
  @FXML private void closeAll() {
    editorController.closeAll();
  }

  /** Calls editor {@code quit} method. */
  @FXML private void quit() {
    editorController.quit();
  }

  /** Calls editor {@code undo} method. */
  @FXML private void undo() {
    editorController.undo();
  }

  /** Calls editor {@code redo} method .*/
  @FXML private void redo() {
    editorController.redo();
  }

  /** Calls editor {@code cut} method .*/
  @FXML private void cut() {
    editorController.cut();
  }

  /** Calls editor {@code copy} method .*/
  @FXML private void copy() {
    editorController.copy();
  }

  /** Calls editor {@code paste} method .*/
  @FXML private void paste() {
    editorController.paste();
  }

  /** Calls editor {@code selectAll} method .*/
  @FXML private void selectAll() {
    editorController.selectAll();
  }

  /** Calls editor {@code findReplace} method .*/
  @FXML private void findReplace() {
    editorController.findReplace();
  }

  /** Calls simulator {@code assemble} method .*/
  @FXML private void assemble() {
    simulatorController.assemble();
  }

  /** Calls simulator {@code run} method .*/
  @FXML private void run() {
    simulatorController.run();
  }

  /** Calls simulator {@code step} method .*/
  @FXML private void step() {
    simulatorController.step();
  }

  /** Calls simulator {@code backstep} method .*/
  @FXML private void backstep() {
    simulatorController.backstep();
  }

  /** Calls simulator {@code stop} method .*/
  @FXML private void stop() {
    simulatorController.stop();
  }

  /** Calls simulator {@code reset} method .*/
  @FXML private void reset() {
    simulatorController.reset();
  }

  /** Calls simulator {@code clearAllBreakpoints} method .*/
  @FXML private void clearAllBreakpoints() {
    simulatorController.clearAllBreakpoints();
  }

  /** Calls settings {@code toggleShowSymbolTable} method. */
  @FXML private void showSymbolTable() {
    Settings.toggleShowSymbolTable();
  }

  /** Calls settings {@code toggleAssembleOnlyOpen} method. */
  @FXML private void assembleOnly() {
    Settings.toggleAssembleOnlyOpen();
  }

  /** Calls settings {@code toggleAssembleAll} method. */
  @FXML private void assembleAll() {
    Settings.toggleAssembleAll();
  }

  /** Calls settings {@code toggleAssemblerWarnings} method. */
  @FXML private void assemblerWarnings() {
    Settings.toggleAssemblerWarnings();
  }

  /** Calls settings {@code togglePermitPseudos} method. */
  @FXML private void permitPseudos() {
    Settings.togglePermitPseudos();
  }

  /** Calls settings {@code toggleSelfModifyingCode} method. */
  @FXML private void selfModifyingCode() {
    Settings.toggleSelfModifyingCode();
  }

  /** Calls settings {@code toggleAutoIndent} method. */
  @FXML private void editorAutoIndent() {
    Settings.toggleAutoIndent();
  }

  /** Calls settings {@code toggleDarkMode} method. */
  @FXML private void editorDarkMode() {
    Settings.toggleDarkMode();
  }

  /** Calls settings {@code setTabSize(2)} method. */
  @FXML private void editorTabSize2() {
    Settings.setTabSize(2);
  }

  /** Calls settings {@code setTabSize(4)} method. */
  @FXML private void editorTabSize4() {
    Settings.setTabSize(4);
  }

  /** Calls settings {@code setTabSize(8)} method. */
  @FXML private void editorTabSize8() {
    Settings.setTabSize(8);
  }

  /** Shows V-Sim about dialog. */
  @FXML private void about() {
    aboutDialog().show();
  }

  /** Initializes controls. */
  private void initControls() {
    // add main tab pane listener
    mainTabPane.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> {
      if (n == editorTab) {
        Status.READY.set(false);
      }
    });
    // add bindings
    simulatorTab.disableProperty().bind(Bindings.not(Status.READY));
    editorTab.disableProperty().bind(Status.RUNNING);
    showSymbolTable.selectedProperty().bind(Settings.SHOW_ST);
    assembleOnly.selectedProperty().bind(Settings.ASSEMBLE_ONLY_OPEN);
    assembleAll.selectedProperty().bind(Settings.ASSEMBLE_ALL);
    assemblerWarnings.selectedProperty().bind(Settings.ASSEMBLER_WARNINGS);
    permitPseudos.selectedProperty().bind(Settings.PERMIT_PSEUDOS);
    selfModifyingCode.selectedProperty().bind(Settings.SELF_MODIFYING);
    autoIndent.selectedProperty().bind(Settings.AUTO_INDENT);
    darkMode.selectedProperty().bind(Settings.DARK_MODE);
    tabSize2.selectedProperty().bind(Bindings.equal(2, Settings.TAB_SIZE));
    tabSize4.selectedProperty().bind(Bindings.equal(4, Settings.TAB_SIZE));
    tabSize8.selectedProperty().bind(Bindings.equal(8, Settings.TAB_SIZE));
    // disable some file menu items if there are no tabs open
    ReadOnlyBooleanProperty editorSelected = editorTab.selectedProperty();
    ReadOnlyBooleanProperty simulatorSelected = simulatorTab.selectedProperty();
    BooleanBinding fileCond = Bindings.or(Bindings.isEmpty(editorController.getEditorTabPane().getTabs()), simulatorSelected);
    newFile.disableProperty().bind(simulatorSelected);
    openFile.disableProperty().bind(simulatorSelected);
    changeProjectFolder.disableProperty().bind(simulatorSelected);
    save.disableProperty().bind(fileCond);
    saveAs.disableProperty().bind(fileCond);
    saveAll.disableProperty().bind(fileCond);
    close.disableProperty().bind(fileCond);
    closeAll.disableProperty().bind(fileCond);
    undo.disableProperty().bind(fileCond);
    redo.disableProperty().bind(fileCond);
    cut.disableProperty().bind(fileCond);
    copy.disableProperty().bind(fileCond);
    paste.disableProperty().bind(fileCond);
    selectAll.disableProperty().bind(fileCond);
    findAndReplace.disableProperty().bind(fileCond);
    assemble.disableProperty().bind(Bindings.or(Bindings.isEmpty(editorController.getEditorTabPane().getTabs()), Bindings.or(Status.READY, assembling)));
    run.disableProperty().bind(Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT)));
    step.disableProperty().bind(Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT)));
    backstep.disableProperty().bind(Bindings.or(Status.EMPTY, Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT))));
    stop.disableProperty().bind(Bindings.or(Bindings.not(Status.RUNNING), Bindings.or(editorSelected, Status.EXIT)));
    reset.disableProperty().bind(Bindings.or(Status.EMPTY, Bindings.or(editorSelected, Status.EXIT)));
    clearAllBreakpoints.disableProperty().bind(Bindings.or(Status.RUNNING, editorSelected));
    // console tab
    InlineCssTextArea console = new InlineCssTextArea();
    console.setId("console");
    // clear option
    MenuItem clear = new MenuItem("clear");
    clear.setOnAction(e -> console.replaceText(0, console.getLength(), ""));
    clear.setGraphic(Icons.get("clear"));
    // copy option
    MenuItem copy = new MenuItem("copy");
    copy.setOnAction(e -> console.copy());
    copy.setGraphic(Icons.get("copy"));
    // select all option
    MenuItem selectAll = new MenuItem("select all");
    selectAll.setOnAction(e -> console.selectAll());
    selectAll.setGraphic(Icons.get("select"));
    // set context menu with this options
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(clear, copy, selectAll);
    console.setContextMenu(menu);
    IO.setStdin(new GUIConsoleInput(console));
    IO.setStdout(new GUIConsoleOutput(console));
    IO.setStderr(new GUIConsoleOutput(console));
    consoleTab.setContent(new VirtualizedScrollPane<>(console));
    // scroll listener
    console.setStyle(String.format("-fx-font-size: %d;", Settings.CONSOLE_FONT_SIZE.get()));
    console.addEventFilter(ScrollEvent.SCROLL, e -> {
      if (e.isControlDown()) {
        if (e.getDeltaY() > 0)
          Settings.incConsoleFontSize();
        else if (e.getDeltaY() < 0)
          Settings.decConsoleFontSize();
        console.setStyle(String.format("-fx-font-size: %d;", Settings.CONSOLE_FONT_SIZE.get()));
        e.consume();
      }
    });
  }

}
