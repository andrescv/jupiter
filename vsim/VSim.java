/*
Copyright (C) 2018 Andres Castellanos

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

import vsim.utils.Cmd;
import vsim.utils.Message;
import java.util.ArrayList;
import vsim.simulator.Simulator;


/**
 * The VSim class contains the main method of V-Sim simulator.
 */
public final class VSim {

  /**
   * This method is used to launch the V-Sim simulator.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // run this only if some argument(s) is/are passed
    if (args.length > 0) {
      // parse arguments
      ArrayList<String> files = Cmd.parse(args);
      // simulate or debug
      Cmd.title();
      // only if files are provided
      if (files.size() > 0) {
        if (!Settings.DEBUG)
          Simulator.simulate(files);
        else
          Simulator.debug(files);
      } else
        Message.panic("no input files");
    }
  }

}
