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

import com.sun.javafx.application.LauncherImpl;

import vsim.Logger;
import vsim.VSim;
import vsim.gui.controllers.Main;


/** V-Sim GUI application. */
public final class App extends Application {

  /** scenen */
  private Scene scene;
  /** main controller */
  private Main controller;

  /** {@inheritDoc} */
  @Override
  public void init() {
    try {
      // load file
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/vsim/fxml/Main.fxml"));
      Parent root = loader.load();
      controller = loader.getController();
      // create scene
      scene = new Scene(root, 1024, 800);
      // set styles
      scene.getStylesheets().addAll(getClass().getResource("/vsim/css/vsim.css").toExternalForm());
    } catch (IOException e) {
      Logger.error("could not load V-Sim GUI");
      VSim.exit(1);
    }
  }

  /** {@inheritDoc} */
  @Override
  public void start(Stage stage) {
    stage.setTitle("V-Sim");
    stage.getIcons().add(Icons.favicon());
    controller.setStage(stage);
    stage.setScene(scene);
    stage.show();
    stage.toFront();
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
    LauncherImpl.launchApplication(App.class, Splash.class, args);
  }

}
