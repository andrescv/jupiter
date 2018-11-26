package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import com.jfoenix.controls.JFXTabPane;


public class EditorController {

  private MainController mainController;
  @FXML private TreeView<String> tree;
  @FXML private JFXTabPane editor;

  public void initialize(MainController controller) {
    this.mainController = controller;
  }

}
