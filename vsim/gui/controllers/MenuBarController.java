package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.event.Event;
import javafx.event.ActionEvent;
import javafx.application.Platform;
import javafx.scene.control.MenuItem;


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
  /** Edit menu find in buffer option */
  @FXML protected MenuItem findInBuffer;
  /** Edit menu replace in buffer option */
  @FXML protected MenuItem replaceInBuffer;
  /** Edit menu preferences option */
  @FXML protected MenuItem preferences;

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
  }

  @FXML protected void newFile(ActionEvent event) {
    this.mainController.editorController.addNewUntitledTab();
  }

  @FXML protected void openFile(ActionEvent event) {
    this.mainController.editorController.addTitledTab();
  }

  @FXML protected void openFolder(ActionEvent event) {

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

}
