package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;


/**
 * Main controller class.
 */
public class MainController {

  /** Primary stage */
  protected Stage stage;

  /** Reference to editor controller */
  @FXML protected EditorController editorController;
  /** Reference to menubar controller */
  @FXML protected MenuBarController menuBarController;
  /** Reference to simulator controller */
  @FXML protected SimulatorController simulatorController;

  /**
   * Initialize main controller class and other controllers.
   *
   * @param stage primary stage
   */
  public void initialize(Stage stage) {
    this.stage = stage;
    this.editorController.initialize(this);
    this.menuBarController.initialize(this);
    this.simulatorController.initialize(this);
  }

}
