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

import vsim.Logger;
import vsim.gui.controllers.Main;


/** V-Sim GUI application. */
public final class App extends Application {

  /** {@inheritDoc} */
  @Override
  public void start(Stage stage) {
    try {
      // create scene
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/vsim/fxml/Main.fxml"));
      Parent root = loader.load();
      Main controller = loader.getController();
      controller.initialize(stage);
      Scene scene = new Scene(root, 1024, 800);
      // add styles
      scene.getStylesheets().addAll(getClass().getResource("/vsim/css/vsim.css").toExternalForm());
      // set stage
      stage.setTitle("V-Sim");
      stage.getIcons().add(Icons.favicon());
      stage.setScene(scene);
      stage.show();
      stage.toFront();
    } catch (IOException e) {
      Logger.error("could not load V-Sim GUI");
      System.exit(1);
    }
  }

  /**
   * Loads V-Sim GUI application.
   *
   * @param args command line arguments
   */
  public static void load(String[] args) {
    System.setProperty("prism.lcdtext", "false");
    Settings.load();
    Fonts.load();
    launch(args);
  }

}
