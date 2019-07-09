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

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;


/** V-Sim file dialog. */
public final class FileDialog {

  private final Stage stage;
  /** file chooser */
  private final FileChooser chooser;

  /**
   * Creates a new file dialog.
   *
   * @param stage dialog stage
   */
  public FileDialog(Stage stage) {
    this.stage = stage;
    chooser = new FileChooser();
    chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
  }

  /**
   * Shows open file dialog.
   *
   * @param title dialog title
   */
  public File open(String title) {
    chooser.setTitle(title);
    File f = chooser.showOpenDialog(stage);
    if (f != null) {
      return f;
    }
    return null;
  }

  /**
   * Shows save file dialog.
   *
   * @param title dialog title
   * @param filename initial file name
   */
  public File save(String title, String filename) {
    chooser.setTitle(title);
    chooser.setInitialFileName(filename);
    File f = chooser.showSaveDialog(stage);
    if (f != null) {
      return f;
    }
    return null;
  }

}
