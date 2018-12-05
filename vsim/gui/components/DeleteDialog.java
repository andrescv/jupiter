package vsim.gui.components;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;


/**
 * This class represents a close dialog.
 */
public final class DeleteDialog {

  /** Dialog stage */
  private Stage stage;
  /** Dialog save button */
  @FXML private JFXButton delete;
  /** Dialog cancel button */
  @FXML private JFXButton cancel;

  /** Save dialog result */
  private boolean result;

  public DeleteDialog() {
    try {
      this.result = false;
      this.stage = new Stage();
      this.stage.setTitle("Delete Item");
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/favicon.png")));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/DeleteDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 440, 160));
      this.cancel.setOnAction(e -> {
        this.result = false;
        this.stage.close();
      });
      this.delete.setOnAction(e -> {
        this.result = true;
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
  public boolean showAndWait() {
    this.stage.showAndWait();
    return this.result;
  }

}
