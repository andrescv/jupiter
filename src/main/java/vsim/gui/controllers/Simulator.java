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


/** V-Sim GUI simulator controller. */
public final class Simulator {

  /** main controller */
  private Main mainController;

  /**
   * Initializes V-Sim's GUI simulator controller.
   *
   * @param mainController main controller
   */
  protected void initialize(Main mainController) {
    this.mainController = mainController;
  }

  /** Assembles RISC-V files. */
  protected void assemble() {

  }

  /** Runs RISC-V program. */
  @FXML protected void run() {

  }

  /** Continue until another instruction reached */
  @FXML protected void step() {

  }

  /** Back to the previous instruction */
  @FXML protected void backstep() {

  }

  /** Stops simulation. */
  @FXML protected void stop() {

  }

  /** Resets simulation. */
  @FXML protected void reset() {

  }

  /** Clears all breakpoints. */
  protected void clearAllBreakpoints() {

  }

}
