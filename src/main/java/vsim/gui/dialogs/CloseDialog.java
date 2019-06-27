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


/** V-Sim GUI close dialog. */
public class CloseDialog extends JFXAlert<Void> {

  /** enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** alert title */
  protected Text title;
  /** body message */
  protected Text body;
  /** Save dialog result */
  private int result;

  /**
   * Creates a default save cancel and dont save dialog.
   *
   * @param stage dialog stage
   */
  public CloseDialog(Stage stage) {
    super(stage);
    // set content
    HBox box = new HBox();
    box.setSpacing(10);
    box.setAlignment(Pos.CENTER);
    JFXButton save = new JFXButton("Save");
    JFXButton cancel = new JFXButton("Cancel");
    JFXButton dontSave = new JFXButton("Don't Save");
    save.setGraphic(Icons.btn("save"));
    save.getStyleClass().add("save");
    cancel.setGraphic(Icons.btn("stop"));
    cancel.getStyleClass().add("cancel");
    dontSave.setGraphic(Icons.btn("dont_save"));
    dontSave.getStyleClass().add("dont-save");
    box.getChildren().addAll(save, cancel, dontSave);
    // set layout
    JFXDialogLayout layout = new JFXDialogLayout();
    title = new Text("Save program changes ?");
    body = new Text("Changes to one or more files will be lost unless you save. Do you wish to save all changes now ?");
    layout.setHeading(title);
    layout.setBody(body);
    layout.setActions(box);
    // set alert
    setContent(layout);
    initModality(Modality.NONE);
    setAnimation(JFXAlertAnimation.CENTER_ANIMATION);
    // save actions
    save.setOnAction(e -> save());
    save.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        save();
    });
    // cancel actions
    cancel.setOnAction(e -> cancel());
    cancel.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        cancel();
    });
    // dont save actions
    dontSave.setOnAction(e -> dontSave());
    dontSave.setOnKeyPressed(e -> {
      if (ENTER.match(e))
        dontSave();
    });
  }

  /** Save action. */
  private void save() {
    result = 1;
    close();
  }

  /** Dont save action. */
  private void dontSave() {
    result = 0;
    close();
  }

  /** Cancel action. */
  private void cancel() {
    result = -1;
    close();
  }

  /**
   * Shows close dialog and returns result code.
   *
   * @return result code
   */
  public int get() {
    result = -1;
    showAndWait();
    return result;
  }

}
