package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import vsim.simulator.Status;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;


/**
 * Menubar controller class.
 */
public class MenuBarController {

  /** File menu new file option */
  @FXML protected MenuItem newFile;
  /** File menu open file option */
  @FXML protected MenuItem openFile;
  /** File menu open folder option */
  @FXML protected MenuItem openFolder;
  /** File menu save option */
  @FXML protected MenuItem save;
  /** File menu save as... option */
  @FXML protected MenuItem saveAs;
  /** File menu save all option */
  @FXML protected MenuItem saveAll;
  /** File menu close tab option */
  @FXML protected MenuItem closeTab;
  /** File menu close all tabs option */
  @FXML protected MenuItem closeAll;
  /** File menu quit option */
  @FXML protected MenuItem quit;

  /** Edit menu undo option */
  @FXML protected MenuItem undo;
  /** Edit menu redo option */
  @FXML protected MenuItem redo;
  /** Edit menu cut option */
  @FXML protected MenuItem cut;
  /** Edit menu copy option */
  @FXML protected MenuItem copy;
  /** Edit menu paste option */
  @FXML protected MenuItem paste;
  /** Edit menu select all option */
  @FXML protected MenuItem selectAll;
  /** Edit menu find/replace in buffer option */
  @FXML protected MenuItem findReplaceInBuffer;

  /** Run menu assemble option */
  @FXML protected MenuItem assemble;
  /** Run menu go option */
  @FXML protected MenuItem go;
  /** Run menu step option */
  @FXML protected MenuItem step;
  /** Run menu backstep option */
  @FXML protected MenuItem backstep;
  /** Run menu reset option */
  @FXML protected MenuItem reset;
  /** Run menu clear breakpoints option */
  @FXML protected MenuItem clearBreakpoints;

  /** Help menu help option */
  @FXML protected MenuItem help;
  /** Help menu about option */
  @FXML protected MenuItem about;

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
    this.backstep.disableProperty().bind(Bindings.or(Status.EMPTY, Bindings.or(Status.RUNNING, Bindings.or(editorSelected, Status.EXIT))));
    this.reset.disableProperty().bind(Bindings.or(Status.EMPTY, Bindings.or(Status.RUNNING, editorSelected)));
    this.clearBreakpoints.disableProperty().bind(Bindings.or(Status.RUNNING, editorSelected));
  }

  /*-------------------------------------------------------*
   |                     File Menu                         |
   *-------------------------------------------------------*/

  @FXML protected void newFile(ActionEvent event) {
    this.mainController.editorController.addNewUntitledTab();
  }

  @FXML protected void openFile(ActionEvent event) {
    this.mainController.editorController.addTitledTab();
  }

  @FXML protected void openFolder(ActionEvent event) {
    this.mainController.editorController.openFolder();
  }

  @FXML protected void save(ActionEvent event) {
    this.mainController.editorController.saveTab();
  }

  @FXML protected void saveAs(ActionEvent event) {
    this.mainController.editorController.saveTabAs();
  }

  @FXML protected void saveAll(ActionEvent event) {
    this.mainController.editorController.saveAllTabs();
  }

  @FXML protected void closeTab(ActionEvent event) {
    this.mainController.editorController.closeTab();
  }

  @FXML protected void closeAllTabs(ActionEvent event) {
    this.mainController.editorController.closeAllTabs();
  }

  @FXML protected void quit(Event event) {
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

   @FXML protected void undo(ActionEvent e) {
     this.mainController.editorController.undo();
   }

   @FXML protected void redo(ActionEvent e) {
     this.mainController.editorController.redo();
   }

   @FXML protected void cut(ActionEvent e) {
     this.mainController.editorController.cut();
   }

   @FXML protected void copy(ActionEvent e) {
     this.mainController.editorController.copy();
   }

   @FXML protected void paste(ActionEvent e) {
     this.mainController.editorController.paste();
   }

   @FXML protected void selectAll(ActionEvent e) {
     this.mainController.editorController.selectAll();
   }

   @FXML protected void findReplaceInBuffer(ActionEvent e) {
     this.mainController.editorController.findReplaceInBuffer();
   }

  /*-------------------------------------------------------*
   |                      Run Menu                         |
   *-------------------------------------------------------*/

  @FXML protected void assemble(ActionEvent e) {
    this.mainController.simulatorController.assemble();
  }

  @FXML protected void go(ActionEvent e) {
    this.mainController.simulatorController.go();
  }

  @FXML protected void step(ActionEvent e) {
    this.mainController.simulatorController.step();
  }

  @FXML protected void backstep(ActionEvent e) {
    this.mainController.simulatorController.backstep();
  }

  @FXML protected void reset(ActionEvent e) {
    this.mainController.simulatorController.reset();
  }

  @FXML protected void clearAllBreakpoints(ActionEvent e) {
    this.mainController.simulatorController.clearAllBreakpoints();
  }

}
