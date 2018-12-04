package vsim.gui.components;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;


/**
 * This class represents a close dialog.
 */
public final class CloseDialog {

  /** Dialog stage */
  private Stage stage;
  /** Dialog save button */
  @FXML private JFXButton save;
  /** Dialog cancel button */
  @FXML private JFXButton cancel;
  /** Dialog dont save button */
  @FXML private JFXButton dontSave;

  /** Save dialog result */
  private int result;

  public CloseDialog() {
    try {
      this.result = -1;
      this.stage = new Stage();
      this.stage.setTitle("Save program changes ?");
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/favicon.png")));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/CloseDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 635, 160));
      this.save.setOnAction(e -> {
        this.result = 1;
        this.stage.close();
      });
      this.cancel.setOnAction(e -> {
        this.result = -1;
        this.stage.close();
      });
      this.dontSave.setOnAction(e -> {
        this.result = 0;
        this.stage.close();
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Shows close dialog and returns result code.
   *
   * @return result code
   */
  public int showAndWait() {
    this.stage.showAndWait();
    return this.result;
  }

}
