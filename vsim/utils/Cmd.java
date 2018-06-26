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
import java.io.StringReader;
import java.io.BufferedReader;


/**
 * The class Cmd implements a basic command line interface.
 */
public final class Cmd {

  private Cmd() { /* NOTHING */}

  // newline
  private static final String newline = System.getProperty("line.separator");

  // VSim header
  private static final String HEADER = "__   _____ _" + newline +
                                       "\\ \\ / / __(_)_ ___" + newline +
                                       " \\ V /\\__ \\ | '   \\" + newline +
                                       "  \\_/ |___/_|_|_|_|";

  // slogan and license
  private static final String SUBHEADER = "RISC-V Assembler & Runtime Simulator" + newline +
                                          newline + "GPL-3.0 License" + newline +
                                          "Copyright (c) 2018 Andres Castellanos" + newline +
                                          "All Rights Reserved." + newline +
                                          "See the file LICENSE for a full copyright notice." + newline;

  // usage [-h]
  private static final String USAGE = "usage: vsim [flags] <files>" +
                                      newline + newline + "optional flags:" + newline +
                                      "  -h        show this help message and exit" + newline +
                                      "  -bare     bare machine (no pseudo-ops)" + newline +
                                      "  -quiet    do not print warnings" + newline +
                                      "  -nocolor  do not colorize output (only on linux)" + newline +
                                      "  -debug    start the debugger" + newline +
                                      "  -version  display the current version of the simulator";

  /**
   * This method implements a simple argument parser.
   *
   * @param args arguments to parse
   * @return an ArrayList of RISC-V assembler filenames to simulate (if any)
   */
  public static ArrayList<String> parse(String[] args) {
    int lastArg = 0;
    int firstFile = Math.max(0, args.length - 1);
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("-")) {
        lastArg = i;
        String option = args[i].substring(1);
        if (option.equals("h")) {
          Cmd.title();
          Cmd.exit();
        }
        else if (option.equals("bare"))
          Settings.BARE = true;
        else if (option.equals("quiet"))
          Settings.QUIET = true;
        else if (option.equals("nocolor"))
          Settings.COLORIZE = false;
        else if (option.equals("debug"))
          Settings.DEBUG = true;
        else if (option.equals("version")) {
          System.out.println(Settings.VERSION);
          System.exit(0);
        } else {
          Cmd.title();
          Message.warning("unknown argument: " + args[i] + newline);
          Cmd.exit();
        }
      } else
        firstFile = Math.min(firstFile, i);
    }
    if (firstFile < lastArg) {
      Message.warning("unexpected argument: " + args[firstFile] + newline);
      Cmd.exit();
    }
    ArrayList<String> files = new ArrayList<String>();
    for (int i = firstFile; i < args.length; i++) {
      if (!args[i].startsWith("-"))
        files.add(args[i]);
    }
    files.trimToSize();
    return files;
  }

  /**
   * This method prints the title of the VSim simulator.
   */
  public static void title() {
    if (Settings.COLORIZE) {
      try {
        String out = "";
        BufferedReader br = new BufferedReader(new StringReader(Cmd.HEADER));
        out += Colorize.red(br.readLine()) + newline;
        out += Colorize.green(br.readLine()) + newline;
        out += Colorize.blue(br.readLine()) + newline;
        out += Colorize.yellow(br.readLine()) + newline + newline;
        out += Colorize.cyan(Cmd.SUBHEADER.substring(0, 37));
        out += Cmd.SUBHEADER.substring(37);
        System.out.println(out);
        return;
      } catch (Exception e) {/* DO NOTHING */}
    }
    // if error/exception print title without color
    System.out.println(Cmd.HEADER + newline);
    System.out.println(Cmd.SUBHEADER);
  }

  /**
   * This method prints the prompt of the VSim simulator.
   */
  public static void prompt() {
    System.out.print(Colorize.red(">") + Colorize.green(">") + Colorize.blue("> "));
  }

  /**
   * This method prints the usage of the VSim simulator and then exits.
   */
  public static void exit() {
    System.out.println(Cmd.USAGE);
    System.exit(0);
  }

}
