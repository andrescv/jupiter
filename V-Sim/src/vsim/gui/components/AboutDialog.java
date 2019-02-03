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
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXDialog;
import vsim.Globals;


/**
 * Simple class representing an about dialog. This class reuse Preloader fxml file.
 */
public final class AboutDialog extends JFXDialog {

  /** version label */
  @FXML private Label version;
  /** copyright label */
  @FXML private Label copyright;

  /**
   * Creates a new about dialog.
   */
  public AboutDialog(StackPane root) throws IOException {
    super();
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Preloader.fxml"));
    loader.setController(this);
    Parent content = loader.load();
    this.version.setText(Globals.VERSION);
    this.copyright.setText(Globals.COPYRIGHT);
    this.setDialogContainer(root);
    this.setContent((Region) content);
    this.setTransitionType(JFXDialog.DialogTransition.TOP);
  }

}
