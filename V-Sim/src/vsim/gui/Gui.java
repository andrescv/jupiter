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
import vsim.gui.controllers.MainController;
import vsim.gui.utils.Icons;


public final class Gui extends Application {

  @Override
  public void init() {
    // wait 2 seconds before start :)
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("V-Sim");
    stage.getIcons().add(Icons.getFavicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Main.fxml"));
    Parent root = loader.load();
    MainController controller = (MainController) loader.getController();
    controller.initialize(stage);
    stage.setMaximized(true);
    // create scene
    Scene scene = new Scene(root, 1024, 800);
    // add styles
    scene.getStylesheets().addAll(getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
        getClass().getResource("/css/vsim-fonts.css").toExternalForm(),
        getClass().getResource("/css/vsim.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
    stage.toFront();
    // clear splitpane divider
    controller.getSimulatorController().removeSplitPaneDivider();
  }
}
