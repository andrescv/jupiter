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

package vsim.gui.dialogs;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import com.jfoenix.controls.JFXDialog;

import vsim.Globals;
import vsim.gui.Icons;


/** V-Sim GUI about dialog. */
public final class AboutDialog extends JFXDialog {

  /**
   * Creates a new about dialog.
   *
   * @param root root stack pane.
   */
  public AboutDialog(StackPane root) {
    super();
    AnchorPane anchor = new AnchorPane();
    anchor.setMaxHeight(274);
    anchor.setMaxWidth(500);
    anchor.setMinHeight(274);
    anchor.setMaxWidth(500);
    ImageView about = Icons.about();
    Label version = new Label(Globals.VERSION);
    Label license = new Label(Globals.LICENSE);
    version.getStyleClass().add("about");
    license.getStyleClass().add("about");
    anchor.setTopAnchor(about, 0.0);
    anchor.setBottomAnchor(about, 0.0);
    anchor.setLeftAnchor(about, 0.0);
    anchor.setRightAnchor(about, 0.0);
    anchor.setTopAnchor(version, 5.0);
    anchor.setRightAnchor(version, 10.0);
    anchor.setBottomAnchor(license, 5.0);
    anchor.setLeftAnchor(license, 10.0);
    anchor.getChildren().addAll(about, version, license);
    setDialogContainer(root);
    setContent((Region) anchor);
    setTransitionType(JFXDialog.DialogTransition.TOP);
  }

}
