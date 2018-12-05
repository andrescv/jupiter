package vsim.gui.components;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXDecorator;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;


/**
 * This class represents a text input dialog.
 */
public final class InputDialog {

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** If user hits enter key */
  private boolean enterPressed;
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
  public InputDialog(String title) {
    try {
      this.stage = new Stage();
      this.stage.setTitle(title);
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/favicon.png")));
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/InputDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      this.enterPressed = false;
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 300, 140));
      this.enter.setOnAction(e -> {
        this.enterPressed = true;
        this.stage.close();
      });
      this.text.setOnKeyPressed(e -> {
        if (InputDialog.ENTER.match(e)) {
          this.enterPressed = true;
          this.stage.close();
        }
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates an input dialog without a default "Enter your input..." title.
   */
  public InputDialog() {
    this("Enter your input...");
  }

  /**
   * Shows input dialog and returns user input text.
   *
   * @return user input text
   */
  public String showAndWait() {
    this.text.requestFocus();
    this.stage.showAndWait();
    String data = "";
    if (this.enterPressed)
      data = this.text.getText();
    this.text.setText("");
    this.enterPressed = false;
    return data;
  }

}
