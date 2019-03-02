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
import java.io.File;
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


/** This class represents a delete dialog. */
public final class DeleteDialog extends JFXAlert<Void> {

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** Save dialog result */
  private boolean result;

  /** Creates a new delete dialog */
  public DeleteDialog(Stage stage, File file) {
    super(stage);
    JFXDialogLayout layout = new JFXDialogLayout();
    layout.setHeading(new Text("Are you sure you want to delete the selected item permanently ?"));
    layout.setBody(new Text("You are deleting: " + file));
    HBox box = new HBox();
    box.setSpacing(10);
    box.setAlignment(CENTER);
    JFXButton delete = new JFXButton("Delete");
    JFXButton cancel = new JFXButton("Cancel");
    delete.setGraphic(Icons.getImage("saveBtn"));
    delete.setCursor(Cursor.HAND);
    delete.setFont(Font.font("Roboto Bold", 14.0));
    delete.setStyle("-fx-background-color: #d32f2f;");
    delete.setTextFill(Color.WHITE);
    cancel.setGraphic(Icons.getImage("cancelBtn"));
    cancel.setCursor(Cursor.HAND);
    cancel.setFont(Font.font("Roboto Bold", 14.0));
    cancel.setTextFill(Color.WHITE);
    cancel.setStyle("-fx-background-color: #388e3c;");
    box.getChildren().addAll(delete, cancel);
    layout.setActions(box);
    this.setContent(layout);
    this.initModality(Modality.NONE);
    this.setAnimation(JFXAlertAnimation.TOP_ANIMATION);
    // save actions
    delete.setOnAction(e -> this.delete());
    delete.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        this.delete();
    });
    // cancel actions
    cancel.setOnAction(e -> this.cancel());
    cancel.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        this.cancel();
    });
  }

  /** Delete action. */
  private void delete() {
    this.result = true;
    this.close();
  }

  /** Cancel action. */
  private void cancel() {
    this.result = false;
    this.close();
  }

  /**
   * Shows close dialog and returns result code.
   *
   * @return result code
   */
  public boolean get() {
    this.result = false;
    this.showAndWait();
    return this.result;
  }
}
