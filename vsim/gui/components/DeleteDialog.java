package vsim.gui.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import vsim.gui.utils.Icons;

/** This class represents a close dialog. */
public final class DeleteDialog {

  /** Escape key combination */
  private static final KeyCodeCombination ESCAPE = new KeyCodeCombination(KeyCode.ESCAPE);

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

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
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/resources/fxml/DeleteDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 437, 166));
      // cancel actions
      this.cancel.setOnAction(e -> this.cancel());
      this.cancel.setOnKeyPressed(
          e -> {
            if (DeleteDialog.ENTER.match(e)) this.cancel();
          });
      // delete actions
      this.delete.setOnAction(e -> this.delete());
      this.delete.setOnKeyPressed(
          e -> {
            if (DeleteDialog.ENTER.match(e)) this.delete();
          });
      // stage actions
      this.stage.addEventHandler(
          KeyEvent.KEY_RELEASED,
          e -> {
            if (DeleteDialog.ESCAPE.match(e)) this.cancel();
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Delete action. */
  private void delete() {
    this.result = true;
    this.stage.close();
  }

  /** Cancel action. */
  private void cancel() {
    this.result = false;
    this.stage.hide();
  }

  /**
   * Shows close dialog and returns result code.
   *
   * @return result code
   */
  public boolean showAndWait() {
    this.result = false;
    this.stage.showAndWait();
    return this.result;
  }
}
