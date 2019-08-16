/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package jupiter.gui.dialogs;

import java.io.File;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;



/** Jupiter GUI input dialog. */
public final class InputDialog extends JFXAlert<Void> {

  /** enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** if user hits enter key */
  private boolean enterPressed;

  /** input text */
  private JFXTextField text;

  /** dialog title */
  private Text title;

  /**
   * Creates a new path dialog.
   *
   * @param stage dialog stage
   */
  public InputDialog(Stage stage) {
    super(stage);
    enterPressed = false;
    // set content
    text = new JFXTextField();
    title = new Text();
    // set layout
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(title);
    layout.setBody(text);
    // set alert
    setContent(layout);
    initModality(Modality.NONE);
    setOverlayClose(false);
    setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    // enter actions
    text.setOnKeyPressed(e -> {
      if (ENTER.match(e)) {
        enterPressed = true;
        close();
      }
    });
  }

  /**
   * Shows input dialog and returns user input file.
   *
   * @param title dialog title
   * @param text initial path
   * @param addFileSep if true add a file separator at the end
   * @return user input path
   */
  public File getFile(String title, String text, boolean addFileSep) {
    File file = null;
    // prepare dialog
    if (addFileSep && !text.endsWith(File.separator)) {
      this.text.setText(text + File.separator);
    } else {
      this.text.setText(text);
    }
    this.text.positionCaret(this.text.getLength());
    this.text.requestFocus();
    this.title.setText(title);
    // wait for input
    showAndWait();
    // get data if enter pressed
    if (enterPressed && this.text.getText().length() != 0) {
      file = new File(this.text.getText());
    }
    // clear state
    this.text.setText("");
    enterPressed = false;
    return file;
  }

  /**
   * Shows input dialog and returns user input text.
   *
   * @param title dialog title
   * @param text initial text
   * @return user input text
   */
  public String get(String title, String text) {
    this.text.setText(text);
    this.text.positionCaret(this.text.getLength());
    this.text.requestFocus();
    this.title.setText(title);
    // wait for input
    showAndWait();
    // get data if enter pressed
    String data = null;
    if (enterPressed && this.text.getText().length() != 0) {
      data = this.text.getText();
    }
    // clear state
    this.text.setText("");
    enterPressed = false;
    return data;
  }

}
