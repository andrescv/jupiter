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

import javafx.fxml.FXML;
import javafx.stage.Stage;


/** V-Sim GUI main controller. */
public final class Main {

  /** editor controller */
  @FXML protected Editor editorController;
  /** simulator controller */
  @FXML protected Simulator simulatorController;

  /**
   * Initializes V-Sim's GUI main controller.
   *
   * @param stage main stage
   */
  public void initialize(Stage stage) {
    // init other controllers
    editorController.initialize(this);
    simulatorController.initialize(this);
  }

}
