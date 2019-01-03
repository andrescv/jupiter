package vsim.gui.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import javafx.application.Platform;
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

/** This class represents a text input dialog. */
public final class InputDialog {

  /** Escape key combination */
  private static final KeyCodeCombination ESCAPE = new KeyCodeCombination(KeyCode.ESCAPE);

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** If user hits enter key */
  private boolean enterPressed;
  /** user input queue */
  private final ArrayBlockingQueue<String> queue;
  /** Dialog stage */
  private Stage stage;
  /** Dialog enter button */
  @FXML private JFXButton enter;
  /** Dialog text field */
  @FXML private JFXTextField text;

  /**
   * Creates an input dialog with a default title.
   *
   * @param title dialog title
   */
  public InputDialog() {
    try {
      this.stage = new Stage();
      this.queue = new ArrayBlockingQueue<String>(1);
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader =
          new FXMLLoader(getClass().getResource("/resources/fxml/InputDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.enterPressed = false;
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 304, 150));
      // enter actions
      this.enter.setOnAction(e -> this.enter());
      this.enter.setOnKeyPressed(
          e -> {
            if (InputDialog.ENTER.match(e)) this.enter();
          });
      this.text.setOnKeyPressed(
          e -> {
            if (InputDialog.ENTER.match(e)) this.enter();
          });
      // stage actions
      this.stage.addEventHandler(
          KeyEvent.KEY_RELEASED,
          e -> {
            if (InputDialog.ESCAPE.match(e)) this.cancel();
          });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Cancel action. */
  private void cancel() {
    this.enterPressed = false;
    this.stage.hide();
  }

  /** Enter action. */
  private void enter() {
    this.enterPressed = true;
    this.stage.hide();
  }

  /**
   * Shows input dialog and returns user input text.
   *
   * @return user input text
   */
  public String getInput(String title) {
    Platform.runLater(
        () -> {
          // clean text field
          this.text.setText("");
          this.enterPressed = false;
          // request input focus
          this.stage.setTitle(title);
          this.text.requestFocus();
          this.stage.showAndWait();
          if (this.enterPressed) this.queue.offer(this.text.getText());
          else this.queue.offer("");
        });
    try {
      return this.queue.take();
    } catch (InterruptedException e) {
      return "";
    } finally {
      this.queue.clear();
    }
  }

  /**
   * Shows input dialog and returns user input text.
   *
   * @param title dialog title
   * @return user input text
   */
  public String showAndWait(String title) {
    String data = "";
    this.stage.setTitle(title);
    // request input focus
    this.text.requestFocus();
    this.stage.showAndWait();
    if (this.enterPressed) data = this.text.getText();
    this.text.setText("");
    this.enterPressed = false;
    return data;
  }
}
