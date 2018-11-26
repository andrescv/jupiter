package vsim.gui.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


public class MainController implements Initializable {

  private Stage stage;

  @FXML private EditorController editorController;
  @FXML private MenuBarController menuBarController;
  @FXML private SimulatorController simulatorController;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.editorController.initialize(this);
    this.menuBarController.initialize(this);
    this.simulatorController.initialize(this);
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public Stage getStage() {
    return this.stage;
  }

}
