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

package vsim.gui.dialogs;

import java.nio.file.Path;

import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import com.jfoenix.animation.alert.JFXAlertAnimation;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;

import vsim.gui.Icons;


/** V-Sim GUI delete dialog. */
public final class DeleteDialog extends JFXAlert<Void> {

  /** enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** body message */
  protected Text body;
  /** save dialog result */
  private boolean result;

  /**
   * Creates a new delete dialog.
   *
   * @param stage dialog stage
   * @param file file to delete
   */
  public DeleteDialog(Stage stage) {
    super(stage);
    // set content
    HBox box = new HBox();
    box.setSpacing(10);
    box.setAlignment(Pos.CENTER);
    JFXButton delete = new JFXButton("Delete");
    JFXButton cancel = new JFXButton("Cancel");
    delete.setGraphic(Icons.btn("trash"));
    delete.getStyleClass().add("delete");
    cancel.setGraphic(Icons.btn("stop"));
    cancel.getStyleClass().add("cancel");
    box.getChildren().addAll(delete, cancel);
    // set layout
    JFXDialogLayout layout = new JFXDialogLayout();
    body = new Text("");
    layout.setHeading(new Text("Are you sure you want to delete the selected item permanently ?"));
    layout.setBody(body);
    layout.setActions(box);
    // set alert
    setContent(layout);
    initModality(Modality.NONE);
    setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    // save actions
    delete.setOnAction(e -> delete());
    delete.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        delete();
    });
    // cancel actions
    cancel.setOnAction(e -> cancel());
    cancel.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        cancel();
    });
  }

  /** Delete action. */
  private void delete() {
    result = true;
    close();
  }

  /** Cancel action. */
  private void cancel() {
    result = false;
    close();
  }

  /**
   * Shows delete dialog and returns result code.
   *
   * @return result code
   */
  public boolean get(Path file) {
    result = false;
    body.setText("You are deleting: " + file);
    showAndWait();
    return result;
  }
}
