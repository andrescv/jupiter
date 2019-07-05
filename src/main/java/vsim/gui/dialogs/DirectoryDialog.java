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

import java.io.File;
import java.nio.file.Path;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import vsim.gui.Settings;


/** V-Sim file dialog. */
public final class DirectoryDialog {

  /** dialog stage */
  private final Stage stage;
  /** file chooser */
  private final DirectoryChooser chooser;

  /**
   * Creates a new file dialog.
   *
   * @param stage dialog stage
   */
  public DirectoryDialog(Stage stage) {
    this.stage = stage;
    chooser = new DirectoryChooser();
  }

  /**
   * Shows directory dialog.
   *
   * @param title dialog title
   */
  public Path open(String title) {
    chooser.setTitle(title);
    chooser.setInitialDirectory(new File(Settings.USER_DIR.get()));
    File f = chooser.showDialog(stage);
    if (f != null) {
      return f.toPath();
    }
    return null;
  }

}
