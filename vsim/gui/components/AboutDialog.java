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
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXDecorator;
import vsim.Settings;
import vsim.gui.utils.Icons;


/**
 * Simple class representing an about dialog. This class reuse Preloader fxml file.
 */
public final class AboutDialog {

  /** Dialog stage */
  private Stage stage;
  /** version label */
  @FXML private Label version;
  /** copyright label */
  @FXML private Label copyright;

  /**
   * Creates a new about dialog.
   */
  public AboutDialog() {
    try {
      this.stage = new Stage();
      this.stage.setTitle("V-Sim About");
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Preloader.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.stage.setResizable(false);
      Scene scene = new Scene(decorator, 504, 312);
      scene.getStylesheets().addAll(getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
          getClass().getResource("/css/vsim-fonts.css").toExternalForm());
      this.stage.setScene(scene);
      this.version.setText(Settings.VERSION);
      this.copyright.setText(Settings.COPYRIGHT);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Shows about dialog.
   */
  public void show() {
    this.stage.show();
  }

}
