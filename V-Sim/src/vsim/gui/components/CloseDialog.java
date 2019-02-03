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


/** This class represents a close dialog. */
public final class CloseDialog extends SaveCancelDontSave {

  /** Creates a new Close Dialog */
  public CloseDialog(Stage stage) {
    super(stage, "Save program changes ?",
        "Changes to one or more files will be lost unless you save. Do you wish to save all changes now ?");
  }

}
