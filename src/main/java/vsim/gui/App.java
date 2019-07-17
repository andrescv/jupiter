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

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import vsim.Logger;
import vsim.VSim;


/** V-Sim GUI application. */
public final class App extends Application {

  /** {@inheritDoc} */
  @Override
  public void start(Stage stage) {
    try {
      // load splash
      Parent root = FXMLLoader.load(getClass().getResource("/vsim/fxml/Splash.fxml"));
      // create scene and add styles
      Scene scene = new Scene(root, 500, 274);
      scene.getStylesheets().addAll(getClass().getResource("/vsim/css/splash.css").toExternalForm());
      // set stage
      stage.setScene(scene);
      stage.setResizable(false);
      stage.initStyle(StageStyle.UNDECORATED);
      stage.getIcons().add(Icons.favicon());
      stage.toFront();
      stage.show();
    } catch (IOException e) {
      Logger.error("could not load V-Sim GUI");
      VSim.exit(1);
    }
  }

  /** Loads V-Sim GUI application. */
  public static void load() {
    System.setProperty("prism.lcdtext", "false");
    Settings.load();
    Fonts.load();
    launch(new String[]{});
  }

}
