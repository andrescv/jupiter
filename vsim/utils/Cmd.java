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

package vsim.utils;

import vsim.Settings;
import java.util.ArrayList;


/**
 * The class Cmd implements a basic command line interface.
 */
public final class Cmd {

  private Cmd() { /* NOTHING */ }

  /**
   * This method implements a simple argument parser.
   *
   * @param args arguments to parse
   * @return an array of RISC-V assembler filenames to simulate (if any)
   */
  public static ArrayList<String> parse(String[] args) {
    ArgumentParser parser = new ArgumentParser();
    // simulator available options
    parser.add("-help",    "show this help message and exit");
    parser.add("-bare",    "bare machine (no pseudo-ops)");
    parser.add("-quiet",   "do not print warnings");
    parser.add("-nocolor", "do not colorize output");
    parser.add("-notitle", "do not print title and copyright notice");
    parser.add("-dump",    "dump machine code to a file", true);
    parser.add("-start",   "start program at global label (default: main)", true);
    parser.add("-debug",   "start the debugger");
    parser.add("-version", "show the simulator version and exit");
    // parse args
    parser.parse(args);
    // override default Settings
    if (parser.hasFlag("-bare"))
      Settings.BARE = true;
    if (parser.hasFlag("-quiet"))
      Settings.QUIET = true;
    if (parser.hasFlag("-nocolor"))
      Settings.COLORIZE = false;
    if (parser.hasFlag("-notitle"))
      Settings.TITLE = false;
    if (parser.hasFlag("-dump"))
      Settings.DUMP = parser.value("-dump");
    if (parser.hasFlag("-start"))
      Settings.START = parser.value("-start");
    if (parser.hasFlag("-debug"))
      Settings.DEBUG = true;
    // display usage if errors
    if (parser.hasErrors()) {
      Cmd.title();
      for (String error: parser.getErrors())
        Message.warning(error);
      System.out.println();
      parser.print();
      System.exit(1);
    }
    // first -help
    if (parser.hasFlag("-help")) {
      Cmd.title();
      parser.print();
      System.exit(0);
    }
    // then -version
    if (parser.hasFlag("-version")) {
      IO.stdout.println(Settings.VERSION);
      System.exit(0);
    }
    return parser.targets();
  }

  /**
   * This method prints the title of the VSim simulator.
   */
  public static void title() {
    if (Settings.TITLE) {
      String newline = System.getProperty("line.separator");
      // cool title :]
      IO.stdout.println(Colorize.yellow(" _   __") + Colorize.red("    _____"));
      IO.stdout.println(Colorize.yellow("| | / /") + "___" + Colorize.green("/ __(_)_ _"));
      IO.stdout.println(Colorize.yellow("| |/ /") + "___" + Colorize.blue("/\\ \\/ /  ' \\"));
      IO.stdout.println(Colorize.yellow("|___/") + Colorize.purple("   /___/_/_/_/_/") + newline);
      IO.stdout.println(Colorize.cyan("RISC-V Assembler & Runtime Simulator" + newline));
      IO.stdout.println("GPL-3.0 License");
      IO.stdout.println("Copyright (c) 2018 Andres Castellanos");
      IO.stdout.println("All Rights Reserved");
      IO.stdout.println("See the file LICENSE for a full copyright notice" + newline);
    }
  }

  /**
   * This method prints the prompt of the VSim simulator.
   */
  public static void prompt() {
    IO.stdout.print(Colorize.yellow(">"));
    IO.stdout.print(Colorize.blue(">"));
    IO.stdout.print(Colorize.yellow(">") + " ");
  }

}
