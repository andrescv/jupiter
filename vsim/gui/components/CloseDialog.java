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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;


/**
 * This class represents a close dialog.
 */
public final class CloseDialog {

  /** Escape key combination */
  private static final KeyCodeCombination ESCAPE = new KeyCodeCombination(KeyCode.ESCAPE);

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

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
      // save actions
      this.save.setOnAction(e -> this.save());
      this.save.setOnKeyPressed(e -> {
        if (CloseDialog.ENTER.match(e))
          this.save();
      });
      // cancel actions
      this.cancel.setOnAction(e -> this.cancel());
      this.cancel.setOnKeyPressed(e -> {
        if (CloseDialog.ENTER.match(e))
          this.cancel();
      });
      // dont save actions
      this.dontSave.setOnAction(e -> this.dontSave());
      this.dontSave.setOnKeyPressed(e -> {
        if (CloseDialog.ENTER.match(e))
          this.dontSave();
      });
      // stage actions
      this.stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
        if (CloseDialog.ESCAPE.match(e))
          this.cancel();
      });
      // set focus on save button
      this.save.requestFocus();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Save action.
   */
  private void save() {
    this.result = 1;
    this.stage.close();
  }

  /**
   * Dont save action.
   */
  private void dontSave() {
    this.result = 0;
    this.stage.close();
  }

  /**
   * Cancel action.
   */
  private void cancel() {
    this.result = -1;
    this.stage.close();
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
