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

package vsim.gui.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import vsim.Globals;
import vsim.Logger;
import vsim.VSim;
import vsim.gui.Icons;


/** V-Sim GUI splash screen controller. */
public final class Splash implements Initializable {

  /** app main stage */
  private final Stage stage;

  /** version label */
  @FXML private Label version;
  /** copyright label */
  @FXML private Label copyright;
  /** copyright label */
  @FXML private Label msg;

  /** Creates a new splash screen controller */
  public Splash() {
    stage = new Stage();
  }

  /** {@inheritDoc} */
  @Override
  public void initialize(URL url, ResourceBundle resourceBundle) {
    version.setText(Globals.VERSION);
    copyright.setText(Globals.LICENSE);
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      @Override
      public Void call() {
        final Scene scene = new Scene(new Pane(), 1024, 800);
        Platform.runLater(() -> {
          try {
            // load file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vsim/fxml/Main.fxml"));
            Parent root = loader.load();
            Main controller = loader.getController();
            controller.setStage(stage);
            // set scene
            scene.setRoot(root);
            // set styles
            scene.getStylesheets().addAll(getClass().getResource("/vsim/css/vsim.css").toExternalForm());
          } catch (IOException e) {
            Logger.error("could not load V-Sim GUI");
            VSim.exit(1);
          }
        });
        // wait a little bit
        try {
          Thread.sleep(1500);
        } catch (InterruptedException e) {
          // nothing here :]
        }
        Platform.runLater(() -> msg.setText("load completed"));
        // wait a little bit more x)
        try {
          Thread.sleep(500);
        } catch (InterruptedException e) {
          // nothing here :]
        }
        // show app and hide splash screen
        Platform.runLater(() -> {
          // set stage
          stage.setTitle("V-Sim");
          stage.getIcons().add(Icons.favicon());
          stage.setScene(scene);
          ((Stage) version.getScene().getWindow()).close();
          stage.toFront();
          stage.show();
        });
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

}
