package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
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
  /** Edit menu preferences option */
  @FXML protected MenuItem preferences;

  /** Find menu find in buffer option */
  @FXML protected MenuItem findInBuffer;
  /** Find menu replace in buffer option */
  @FXML protected MenuItem replaceInBuffer;

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
  }

  @FXML protected void newFile(ActionEvent event) {

  }

  @FXML protected void openFile(ActionEvent event) {

  }

  @FXML protected void openFolder(ActionEvent event) {

  }

  @FXML protected void closeTab(ActionEvent event) {

  }

  @FXML protected void closeAllTabs(ActionEvent event) {

  }

  @FXML protected void save(ActionEvent event) {

  }

  @FXML protected void saveAs(ActionEvent event) {

  }

  @FXML protected void saveAll(ActionEvent event) {

  }

  @FXML protected void quit(ActionEvent event) {

  }

}
