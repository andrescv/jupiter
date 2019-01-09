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

import java.io.File;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import com.jfoenix.controls.JFXCheckBox;
import vsim.Settings;
import vsim.gui.components.AboutDialog;
import vsim.gui.components.EditorDialog;
import vsim.gui.components.InputDialog;
import vsim.simulator.Status;


/**
 * Menubar controller class.
 */
public class MenuBarController {

  /** File menu new file option */
  @FXML private MenuItem newFile;
  /** File menu open file option */
  @FXML private MenuItem openFile;
  /** File menu open folder option */
  @FXML private MenuItem openFolder;
  /** File menu save option */
  @FXML private MenuItem save;
  /** File menu save as... option */
  @FXML private MenuItem saveAs;
  /** File menu save all option */
  @FXML private MenuItem saveAll;
  /** File menu close tab option */
  @FXML private MenuItem closeTab;
  /** File menu close all tabs option */
  @FXML private MenuItem closeAll;
  /** File menu quit option */
  @FXML private MenuItem quit;

  /** Edit menu undo option */
  @FXML private MenuItem undo;
  /** Edit menu redo option */
  @FXML private MenuItem redo;
  /** Edit menu cut option */
  @FXML private MenuItem cut;
  /** Edit menu copy option */
  @FXML private MenuItem copy;
  /** Edit menu paste option */
  @FXML private MenuItem paste;
  /** Edit menu select all option */
  @FXML private MenuItem selectAll;
  /** Edit menu find/replace in buffer option */
  @FXML private MenuItem findReplaceInBuffer;

  /** Run menu assemble option */
  @FXML private MenuItem assemble;
  /** Run menu go option */
  @FXML private MenuItem go;
  /** Run menu step option */
  @FXML private MenuItem step;
  /** Run menu backstep option */
  @FXML private MenuItem backstep;
  /** Run menu reset option */
  @FXML private MenuItem reset;
  /** Run menu clear breakpoints option */
  @FXML private MenuItem clearBreakpoints;

  /** Settings menu show labels option */
  @FXML private MenuItem showLabels;
  /** Settings menu show labels checkbox */
  @FXML private JFXCheckBox showLabelsBox;
  /** Settings menu popup for ecalls option */
  @FXML private MenuItem popup;
  /** Settings menu popup for ecalls checkbox */
  @FXML private JFXCheckBox popupBox;
  /** Settings menu assemble only open files option */
  @FXML private MenuItem onlyOpen;
  /** Settings menu assemble only open files checkbox */
  @FXML private JFXCheckBox onlyOpenBox;
  /** Settings menu warnings = errors option */
  @FXML private MenuItem warnings;
  /** Settings menu warnings = errors checkbox */
  @FXML private JFXCheckBox warningsBox;
  /** Settings menu change start label option */
  @FXML private MenuItem start;
  /** Settings menu permit pseudos option */
  @FXML private MenuItem permit;
  /** Settings menu permit pseudos checkbox */
  @FXML private JFXCheckBox permitBox;
  /** Settings menu permit pseudos option */
  @FXML private MenuItem self;
  /** Settings menu permit pseudos checkbox */
  @FXML private JFXCheckBox selfBox;
  /** Settings menu editor option */
  @FXML private MenuItem editor;
  /** Settings menu trap handler option */
  @FXML private MenuItem trap;

  /** Help menu help option */
  @FXML private MenuItem help;
  /** Help menu about option */
  @FXML private MenuItem about;

  /** About dialog */
  private AboutDialog aboutDialog;

  /** Editor dialog */
  private EditorDialog editorDialog;

  /** Reference to Main controller */
  private MainController mainController;

  /**
   * Initialize Menubar controller.
   *
   * @param controller main controller
   */
  protected void initialize(MainController controller) {
    this.mainController = controller;
    // change implicit exit
    Platform.setImplicitExit(false);
    // handle close request
    this.mainController.stage.setOnCloseRequest(this::quit);
    // disable some file menu items if there are no tabs open
    BooleanBinding isEmpty = Bindings.isEmpty(this.mainController.editorController.editor.getTabs());
    BooleanBinding fileCond = Bindings.or(isEmpty, this.mainController.simTab.selectedProperty());
    this.save.disableProperty().bind(fileCond);
    this.saveAs.disableProperty().bind(fileCond);
    this.saveAll.disableProperty().bind(fileCond);
    this.closeTab.disableProperty().bind(fileCond);
    this.closeAll.disableProperty().bind(fileCond);
    this.undo.disableProperty().bind(fileCond);
    this.redo.disableProperty().bind(fileCond);
    this.cut.disableProperty().bind(fileCond);
    this.copy.disableProperty().bind(fileCond);
    this.paste.disableProperty().bind(fileCond);
    this.selectAll.disableProperty().bind(fileCond);
    this.findReplaceInBuffer.disableProperty().bind(fileCond);
    // dont allow assemble the program again
    this.assemble.disableProperty().bind(Status.READY);
    // disable sim flow control if the editor tab is selected
    ReadOnlyBooleanProperty editorSelected = this.mainController.editorTab.selectedProperty();
    this.go.disableProperty().bind(Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT)));
    this.step.disableProperty().bind(Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT)));
    this.backstep.disableProperty()
        .bind(Bindings.or(Status.EMPTY, Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT))));
    this.reset.disableProperty().bind(Bindings.or(Status.EMPTY, Bindings.or(Status.RUNNING, editorSelected)));
    this.clearBreakpoints.disableProperty().bind(Bindings.or(Status.RUNNING, editorSelected));
    // reflect settings
    this.showLabelsBox.setSelected(Settings.SHOW_LABELS);
    this.popupBox.setSelected(Settings.POPUP_ECALL_INPUT);
    this.onlyOpenBox.setSelected(Settings.ASSEMBLE_ONLY_OPEN);
    this.warningsBox.setSelected(Settings.EXTRICT);
    this.permitBox.setSelected(!Settings.BARE);
    this.selfBox.setSelected(Settings.SELF_MODIFYING);
    this.showLabelsBox.setText("");
    this.popupBox.setText("");
    this.onlyOpenBox.setText("");
    this.warningsBox.setText("");
    this.permitBox.setText("");
  }

  /*-------------------------------------------------------*
  |                     File Menu                         |
  *-------------------------------------------------------*/

  @FXML
  private void newFile(ActionEvent event) {
    this.mainController.editorController.addNewUntitledTab();
  }

  @FXML
  private void openFile(ActionEvent event) {
    this.mainController.editorController.addTitledTab();
  }

  @FXML
  private void openFolder(ActionEvent event) {
    this.mainController.editorController.openFolder();
  }

  @FXML
  private void save(ActionEvent event) {
    this.mainController.editorController.saveTab();
  }

  @FXML
  private void saveAs(ActionEvent event) {
    this.mainController.editorController.saveTabAs();
  }

  @FXML
  private void saveAll(ActionEvent event) {
    this.mainController.editorController.saveAllTabs();
  }

  @FXML
  private void closeTab(ActionEvent event) {
    this.mainController.editorController.closeTab();
  }

  @FXML
  private void closeAllTabs(ActionEvent event) {
    this.mainController.editorController.closeAllTabs();
  }

  @FXML
  private void quit(Event event) {
    this.mainController.editorController.quit();
    // only
    if (!this.mainController.editorController.editor.getTabs().isEmpty())
      event.consume();
    else {
      if (event instanceof ActionEvent)
        this.mainController.stage.close();
      Platform.exit();
    }
  }

  /*-------------------------------------------------------*
  |                      Edit Menu                        |
  *-------------------------------------------------------*/

  @FXML
  private void undo(ActionEvent e) {
    this.mainController.editorController.undo();
  }

  @FXML
  private void redo(ActionEvent e) {
    this.mainController.editorController.redo();
  }

  @FXML
  private void cut(ActionEvent e) {
    this.mainController.editorController.cut();
  }

  @FXML
  private void copy(ActionEvent e) {
    this.mainController.editorController.copy();
  }

  @FXML
  private void paste(ActionEvent e) {
    this.mainController.editorController.paste();
  }

  @FXML
  private void selectAll(ActionEvent e) {
    this.mainController.editorController.selectAll();
  }

  @FXML
  private void findReplaceInBuffer(ActionEvent e) {
    this.mainController.editorController.findReplaceInBuffer();
  }

  /*-------------------------------------------------------*
  |                      Run Menu                         |
  *-------------------------------------------------------*/

  @FXML
  private void assemble(ActionEvent e) {
    this.mainController.simulatorController.assemble();
  }

  @FXML
  private void go(ActionEvent e) {
    this.mainController.simulatorController.go();
  }

  @FXML
  private void step(ActionEvent e) {
    this.mainController.simulatorController.step();
  }

  @FXML
  private void backstep(ActionEvent e) {
    this.mainController.simulatorController.backstep();
  }

  @FXML
  private void reset(ActionEvent e) {
    this.mainController.simulatorController.reset();
  }

  @FXML
  private void clearAllBreakpoints(ActionEvent e) {
    this.mainController.simulatorController.clearAllBreakpoints();
  }

  /*-------------------------------------------------------*
  |                    Settings Menu                      |
  *-------------------------------------------------------*/

  @FXML
  private void showLabels(ActionEvent e) {
    this.showLabelsBox.setSelected(Settings.toggleShowLabels());
    this.mainController.simulatorController.showST();
  }

  @FXML
  private void popup(ActionEvent e) {
    this.popupBox.setSelected(Settings.togglePopup());
  }

  @FXML
  private void onlyOpen(ActionEvent e) {
    this.onlyOpenBox.setSelected(Settings.toggleAssembleOnlyOpen());
  }

  @FXML
  private void warnings(ActionEvent e) {
    this.warningsBox.setSelected(Settings.toggleExtrict());
  }

  @FXML
  private void start(ActionEvent e) {
    InputDialog dialog = new InputDialog();
    String start = dialog.showAndWait("Enter new global start label");
    if (start.length() > 0) {
      Settings.setStart(start);
    }
  }

  @FXML
  private void permit(ActionEvent e) {
    this.permitBox.setSelected(!Settings.toggleBare());
  }

  @FXML
  private void self(ActionEvent e) {
    this.selfBox.setSelected(Settings.toggleSelfModifying());
  }

  @FXML
  private void editor(ActionEvent e) {
    if (this.editorDialog == null)
      this.editorDialog = new EditorDialog();
    this.editorDialog.showAndWait();
    this.mainController.editorController.updateSettings();
  }

  @FXML
  private void trap(ActionEvent e) {
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Load RISC-V Trap Handler");
    if (Settings.TRAP != null && Settings.TRAP.exists())
      chooser.setInitialDirectory(new File(Settings.TRAP.getParent()));
    else
      chooser.setInitialDirectory(Settings.DIR);
    chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
    File file = chooser.showOpenDialog(this.mainController.stage);
    if (file != null && file.exists())
      Settings.TRAP = file;
    else
      ; // TODO
  }

  /*-------------------------------------------------------*
  |                      Help Menu                        |
  *-------------------------------------------------------*/

  @FXML
  private void help(ActionEvent e) {
    // TODO
  }

  @FXML
  private void about(ActionEvent e) {
    if (this.aboutDialog == null)
      this.aboutDialog = new AboutDialog();
    this.aboutDialog.show();
  }

}
