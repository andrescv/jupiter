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

import javafx.stage.Stage;


/** This class represents a save dialog. */
public final class SaveDialog extends SaveCancelDontSave {

  /** Creates a new Save dialog */
  public SaveDialog(Stage stage, String filename) {
    super(stage, String.format("'%s' has changes, do you want to save them?", filename),
        "Your changes will be lost if you close this item without saving.");
  }

}
