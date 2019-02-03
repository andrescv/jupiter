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
import vsim.gui.utils.Icons;


/** This class represents a generic close, cancel, dont save dialog. */
class SaveCancelDontSave extends JFXAlert<Void> {

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** Save dialog result */
  private int result;

  /** Creates a default save cancel and dont save dialog */
  public SaveCancelDontSave(Stage stage, String title, String message) {
    super(stage);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Text(title));
    layout.setBody(new Text(message));
    HBox box = new HBox();
    box.setSpacing(10);
    box.setAlignment(CENTER);
    JFXButton save = new JFXButton("Save");
    JFXButton cancel = new JFXButton("Cancel");
    JFXButton dontSave = new JFXButton("Don't Save");
    save.setGraphic(Icons.getImage("saveBtn"));
    save.setCursor(Cursor.HAND);
    save.setFont(Font.font("Roboto Bold", 14.0));
    save.setStyle("-fx-background-color: #388e3c;");
    save.setTextFill(Color.WHITE);
    cancel.setGraphic(Icons.getImage("cancelBtn"));
    cancel.setCursor(Cursor.HAND);
    cancel.setFont(Font.font("Roboto Bold", 14.0));
    cancel.setTextFill(Color.WHITE);
    cancel.setStyle("-fx-background-color: #0277bd;");
    dontSave.setGraphic(Icons.getImage("dontSaveBtn"));
    dontSave.setCursor(Cursor.HAND);
    dontSave.setFont(Font.font("Roboto Bold", 14.0));
    dontSave.setStyle("-fx-background-color: #d32f2f;");
    dontSave.setTextFill(Color.WHITE);
    box.getChildren().addAll(save, cancel, dontSave);
    layout.setActions(box);
    this.setContent(layout);
    this.initModality(Modality.NONE);
    this.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
    // save actions
    save.setOnAction(e -> this.save());
    save.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        this.save();
    });
    // cancel actions
    cancel.setOnAction(e -> this.cancel());
    cancel.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        this.cancel();
    });
    // dont save actions
    dontSave.setOnAction(e -> this.dontSave());
    dontSave.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        this.dontSave();
    });
  }

  /** Save action. */
  private void save() {
    this.result = 1;
    this.close();
  }

  /** Dont save action. */
  private void dontSave() {
    this.result = 0;
    this.close();
  }

  /** Cancel action. */
  private void cancel() {
    this.result = -1;
    this.close();
  }

  /**
   * Shows close dialog and returns result code.
   *
   * @return result code
   */
  public int get() {
    this.result = -1;
    this.showAndWait();
    return this.result;
  }

}
