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
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXDecorator;
import vsim.gui.utils.Icons;


public final class HelpDialog {

  /** Dialog stage */
  private Stage stage;
  /** Dialog save button */
  @FXML private WebView browser;

  public HelpDialog() {
    try {
      this.stage = new Stage();
      this.stage.setTitle("V-Sim Help");
      this.stage.setMinWidth(800);
      this.stage.setMinHeight(480);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/HelpDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, true, true);
      decorator.setCustomMaximize(true);
      this.stage.setScene(new Scene(decorator, 1024, 800));
      this.browser.getEngine().load(getClass().getResource("/docs/index.html").toExternalForm());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void show() {
    this.stage.show();
  }
}
