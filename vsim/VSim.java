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

import java.io.File;
import java.util.ArrayList;
import com.sun.javafx.application.LauncherImpl;
import vsim.gui.Gui;
import vsim.gui.Preloader;
import vsim.simulator.Simulator;
import vsim.utils.Cmd;
import vsim.utils.Message;


/** The VSim class contains the main method of V-Sim simulator AKA Launcher. */
public final class VSim {

  /**
   * This method is used to launch the V-Sim simulator.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // set ROOT path
    Settings.setRootPath();
    // run this only if some argument(s) is/are passed
    if (args.length > 0) {
      // parse arguments
      ArrayList<File> files = Cmd.parse(args);
      // simulate or debug
      Cmd.title();
      // only if files are provided
      if (files.size() > 0) {
        // add trap handler if any
        if (Settings.TRAP != null)
          files.add(0, Settings.TRAP);
        // simulate/debug program
        if (!Settings.DEBUG)
          Simulator.simulate(files);
        else
          Simulator.debug(files);
      } else
        Message.panic("no input files");
    } else {
      Settings.GUI = true;
      System.setProperty("prism.lcdtext", "false");
      Settings.load();
      LauncherImpl.launchApplication(Gui.class, Preloader.class, args);
    }
  }
}
