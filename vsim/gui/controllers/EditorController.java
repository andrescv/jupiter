package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import com.jfoenix.controls.JFXTabPane;

/**
 * Editor controller class.
 */
public class EditorController {

  /** Current directory tree view */
  @FXML protected TreeView<String> tree;
  /** Editor tab pane */
  @FXML protected JFXTabPane editor;

  /** Reference to main controller */
  protected MainController mainController;

  /**
   * Initialize editor controller.
   *
   * @param controller main controller
   */
  protected void initialize(MainController controller) {
    this.mainController = controller;
  }

}
