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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import vsim.Globals;
import vsim.gui.utils.Icons;


/**
 * This class represents V-Sim's GUI preloader.
 */
public final class Preloader extends javafx.application.Preloader {

  /** preloader width */
  private static final double WIDTH = 500.0;
  /** preloader height */
  private static final double HEIGHT = 274.0;

  /** version label */
  @FXML private Label version;

  /** copyright label */
  @FXML private Label copyright;

  /** preloader main stage */
  private Stage stage;

  @Override
  public void start(Stage stage) throws IOException {
    // remove main frame and buttons (close, minimize, maximize...)
    stage.initStyle(StageStyle.UNDECORATED);
    stage.getIcons().add(Icons.getFavicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Preloader.fxml"));
    loader.setController(this);
    Parent root = loader.load();
    this.version.setText(Globals.VERSION);
    this.copyright.setText(Globals.COPYRIGHT);
    // save primary stage
    this.stage = stage;
    stage.setResizable(false);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    stage.setScene(scene);
    // center stage
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    stage.setX((bounds.getWidth() - WIDTH) / 2.0);
    stage.setY((bounds.getHeight() - HEIGHT) / 2.0);
    stage.show();
    // center stage
    stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification info) {
    StateChangeNotification.Type type = info.getType();
    switch (type) {
      case BEFORE_START:
        this.stage.hide();
        break;
      default:
        break;
    }
  }

}
