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

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDecorator;
import vsim.gui.utils.Icons;


/** This class represents a text input dialog. */
public final class SaveDialog {

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
  /** Dialog message */
  @FXML private Label msg;

  /** Save dialog result */
  private int result;

  /**
   * Creates a save dialog.
   *
   * @param filename save dialog title filename
   */
  public SaveDialog() {
    try {
      this.result = -1;
      this.stage = new Stage();
      this.stage.setTitle("Save...");
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SaveDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.stage.setResizable(false);
      Scene scene = new Scene(decorator, 419, 189);
      scene.getStylesheets().addAll(getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
          getClass().getResource("/css/vsim-fonts.css").toExternalForm(),
          getClass().getResource("/css/vsim.css").toExternalForm());
      this.stage.setScene(scene);
      // save actions
      this.save.setOnAction(e -> this.save());
      this.save.setOnKeyPressed(e -> {
        if (SaveDialog.ENTER.match(e))
          this.save();
      });
      // cancel actions
      this.cancel.setOnAction(e -> this.cancel());
      this.cancel.setOnKeyPressed(e -> {
        if (SaveDialog.ENTER.match(e))
          this.cancel();
      });
      // dont save actions
      this.dontSave.setOnAction(e -> this.dontSave());
      this.dontSave.setOnKeyPressed(e -> {
        if (SaveDialog.ENTER.match(e))
          this.dontSave();
      });
      // stage actions
      this.stage.addEventHandler(KeyEvent.KEY_RELEASED, e -> {
        if (SaveDialog.ESCAPE.match(e))
          this.cancel();
      });
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Save action. */
  private void save() {
    this.result = 1;
    this.stage.hide();
  }

  /** Dont save action. */
  private void dontSave() {
    this.result = 0;
    this.stage.hide();
  }

  /** Cancel action. */
  private void cancel() {
    this.result = -1;
    this.stage.hide();
  }

  /**
   * Shows save dialog and returns result code.
   *
   * @return result code
   */
  public int showAndWait(String filename) {
    this.result = -1;
    this.msg.setText(String.format("'%s' has changes, do you want to save them?", filename));
    this.stage.showAndWait();
    return this.result;
  }
}
