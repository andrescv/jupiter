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

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTabPane;

import vsim.gui.Settings;
import vsim.gui.Status;
import vsim.gui.dialogs.*;


/** V-Sim GUI main controller. */
public final class Main {

  /** main stage */
  protected Stage stage;

  /** about dialog */
  private AboutDialog aboutDialog;
  /** close dialog */
  private CloseDialog closeDialog;
  /** save dialog */
  private SaveDialog saveDialog;
  /** file chooser */
  private FileDialog fileDialog;

  /** editor controller */
  @FXML private Editor editorController;
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

  /** show symbol table setting check box */
  @FXML private JFXCheckBox showSymbolTable;
  /** popup dialog for input ecalls setting check box */
  @FXML private JFXCheckBox popupDialog;
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

  /**
   * Initializes V-Sim's GUI main controller.
   *
   * @param stage main stage
   */
  public void initialize(Stage stage) {
    // save primary stage
    this.stage = stage;
    // set loading state
    loading(false);
    // init other controllers
    editorController.initialize(this);
    simulatorController.initialize(this);
    // add bindings
    simulatorTab.disableProperty().bind(Bindings.not(Status.READY));
    editorTab.disableProperty().bind(Status.RUNNING);
    showSymbolTable.selectedProperty().bind(Settings.SHOW_ST);
    popupDialog.selectedProperty().bind(Settings.POPUP_DIALOG);
    assembleAll.selectedProperty().bind(Settings.ASSEMBLE_ALL);
    assemblerWarnings.selectedProperty().bind(Settings.ASSEMBLER_WARNINGS);
    permitPseudos.selectedProperty().bind(Settings.PERMIT_PSEUDOS);
    selfModifyingCode.selectedProperty().bind(Settings.SELF_MODIFYING);
    autoIndent.selectedProperty().bind(Settings.AUTO_INDENT);
    darkMode.selectedProperty().bind(Settings.DARK_MODE);
    tabSize2.selectedProperty().bind(Bindings.equal(2, Settings.TAB_SIZE));
    tabSize4.selectedProperty().bind(Bindings.equal(4, Settings.TAB_SIZE));
    tabSize8.selectedProperty().bind(Bindings.equal(8, Settings.TAB_SIZE));
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

  /** Calls settings {@code togglePopupDialog} method. */
  @FXML private void popupDialog() {
    Settings.togglePopupDialog();
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

  /** Shows V-Sim help. */
  @FXML private void help() {

  }

  /** Shows V-Sim about dialog. */
  @FXML private void about() {
    aboutDialog().show();
  }

}
