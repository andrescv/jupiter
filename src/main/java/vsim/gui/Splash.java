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

package vsim.gui;

import java.io.IOException;

import javafx.application.Preloader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import vsim.Globals;


/** V-Sim Splash screen. */
public final class Splash extends Preloader {

  /** preloader main stage */
  private Stage stage;

  /** version label */
  @FXML private Label version;
  /** copyright label */
  @FXML private Label copyright;

  /** {@inheritDoc} */
  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("V-Sim loading...");
    // remove main frame and buttons (close, minimize, maximize...)
    stage.initStyle(StageStyle.UNDECORATED);
    stage.getIcons().add(Icons.favicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/vsim/fxml/Splash.fxml"));
    loader.setController(this);
    Parent root = loader.load();
    this.version.setText(Globals.VERSION);
    this.copyright.setText(Globals.LICENSE);
    // save primary stage
    this.stage = stage;
    stage.setResizable(false);
    // create scene and add styles
    Scene scene = new Scene(root, 500, 274);
    scene.getStylesheets().addAll(getClass().getResource("/vsim/css/splash.css").toExternalForm());
    // center stage
    stage.toFront();
    stage.setScene(scene);
    stage.centerOnScreen();
    stage.show();
  }

  /** {@inheritDoc} */
  @Override
  public void handleStateChangeNotification(StateChangeNotification info) {
    StateChangeNotification.Type type = info.getType();
    switch (type) {
      case BEFORE_START:
        stage.close();
        break;
      default:
        break;
    }
  }

}
