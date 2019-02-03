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

package vsim.gui.components;

import static javafx.geometry.Pos.CENTER;
import javafx.scene.Cursor;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import vsim.gui.utils.Icons;


/** This class represents a path input dialog. */
public final class PathDialog extends JFXAlert<Void> {

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** If user hits enter key */
  private boolean enterPressed;

  /** Input text */
  private JFXTextField text;

  /** Dialog layout and content */
  private JFXDialogLayout layout;

  /**
   * Creates a path dialog.
   */
  public PathDialog(Stage stage) {
    super(stage);
    this.layout = new JFXDialogLayout();
    this.text = new JFXTextField();
    layout.setBody(this.text);
    HBox box = new HBox();
    box.setSpacing(10);
    box.setAlignment(CENTER);
    JFXButton enter = new JFXButton("Enter");
    enter.setGraphic(Icons.getImage("enterBtn"));
    enter.setCursor(Cursor.HAND);
    enter.setFont(Font.font("Roboto Bold", 14.0));
    enter.setStyle("-fx-background-color: #388e3c;");
    enter.setTextFill(Color.WHITE);
    box.getChildren().addAll(enter);
    layout.setActions(box);
    this.setContent(layout);
    this.initModality(Modality.NONE);
    this.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
    this.text.setFocusColor(Color.web("#fe315c"));
    this.text.setUnFocusColor(Color.web("#fe315c"));
    this.text.setFont(Font.font("Roboto Bold", 14.0));
    this.enterPressed = false;
    // enter actions
    enter.setOnAction(e -> this.enter());
    enter.setOnKeyPressed(e -> {
      if (PathDialog.ENTER.match(e))
        this.enter();
    });
    text.setOnKeyPressed(e -> {
      if (PathDialog.ENTER.match(e))
        this.enter();
    });
  }

  /** Cancel action. */
  private void cancel() {
    this.enterPressed = false;
    this.close();
  }

  /** Enter action. */
  private void enter() {
    this.enterPressed = true;
    this.close();
  }

  /**
   * Shows path dialog and returns user input text.
   *
   * @param title stage title
   * @return user input text
   */
  public String get(String title) {
    String data = "";
    // request input focus
    this.text.requestFocus();
    this.layout.setHeading(new Text(title));
    this.showAndWait();
    if (this.enterPressed)
      data = this.text.getText();
    this.text.setText("");
    this.enterPressed = false;
    return data;
  }

}
