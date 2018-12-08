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

import java.io.File;
import vsim.gui.Gui;
import vsim.utils.Cmd;
import vsim.gui.Preloader;
import vsim.utils.Message;
import java.util.ArrayList;
import vsim.simulator.Simulator;
import com.sun.javafx.application.LauncherImpl;


/**
 * The VSim class contains the main method of V-Sim simulator.
 */
public final class VSim {

  /**
   * This method returns the root absolute path
   *
   * @return root absolute path
   */
  private static String getRootPath() {
    try {
      File f = new File(VSim.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      String path = f.getAbsolutePath();
      path = path.substring(0, path.lastIndexOf('/') + 1);
      return path;
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * This method is used to launch the V-Sim simulator.
   *
   * @param args command line arguments
   */
  public static void main(String[] args) {
    // run this only if some argument(s) is/are passed
    if (args.length > 0) {
      // set ROOT path
      Settings.ROOT = VSim.getRootPath();
      // parse arguments
      ArrayList<String> files = Cmd.parse(args);
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
      LauncherImpl.launchApplication(Gui.class, Preloader.class, args);
    }
  }

}
