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

package vsim.simulator;

import javafx.beans.property.SimpleBooleanProperty;


public final class Status {

  /** if program is assembled and ready to simulate */
  public static final SimpleBooleanProperty READY = new SimpleBooleanProperty(false);

  /** if simulation is running */
  public static final SimpleBooleanProperty RUNNING = new SimpleBooleanProperty(false);

  /** if simulation is stopped by the user */
  public static final SimpleBooleanProperty STOPPED = new SimpleBooleanProperty(false);

  /** if simulation executes an exit/exit2 ecall */
  public static final SimpleBooleanProperty EXIT = new SimpleBooleanProperty(false);

  /** indicates if the history is empty */
  public static final SimpleBooleanProperty EMPTY = new SimpleBooleanProperty(true);

  /**
   * Resets all status flags.
   */
  public static void reset() {
    Status.RUNNING.set(false);
    Status.EXIT.set(false);
    Status.EMPTY.set(true);
    Status.STOPPED.set(false);
  }

}
