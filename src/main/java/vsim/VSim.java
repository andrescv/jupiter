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

package vsim;

import java.awt.GraphicsEnvironment;

import vsim.gui.App;
import vsim.sim.Loader;
import vsim.utils.Cmd;


/** V-Sim launcher. */
public final class VSim {

  /**
   * V-Sim entry point.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    if (args.length > 0) {
      // CLI mode
      Loader.load(Cmd.parse(args));
    } else if (!GraphicsEnvironment.isHeadless()) {
      // GUI mode
      App.load();
    }
  }

  /**
   * Quits V-Sim simulator
   *
   * @param code exit code
   */
  public static void exit(int code) {
    if (Flags.EXIT) {
      System.exit(code);
    } else {
      throw new RuntimeException(String.format("V-Sim(%d)", code));
    }
  }

}
