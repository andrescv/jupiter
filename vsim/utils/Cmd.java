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

import java.io.File;
import vsim.Globals;
import vsim.Settings;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


/**
 * The class Cmd implements a basic command line interface.
 */
public final class Cmd {

  /**
   * This method parses the command line arguments and returns
   * the source filenames passed.
   *
   * @param args arguments to parse
   * @see vsim.utils.ArgumentParser
   * @return an array of RISC-V assembler filenames to simulate (if any)
   */
  public static ArrayList<String> parse(String[] args) {
    ArgumentParser parser = new ArgumentParser("vsim [options] <files>");
    // simulator available options
    parser.add("-help",    "show this help message and exit");
    parser.add("-bare",    "bare machine (no pseudo-ops)");
    parser.add("-quiet",   "do not print warnings");
    parser.add("-nocolor", "do not colorize output");
    parser.add("-usage",   "print usage of an instruction and exit", true);
    parser.add("-notitle", "do not print title and copyright notice");
    parser.add("-dump",    "dump machine code to a file", true);
    parser.add("-start",   "start program at global label (default: main)", true);
    parser.add("-debug",   "start the debugger");
    parser.add("-version", "show the simulator version and exit");
    parser.add("-iset",    "print available RISC-V instructions and exit");
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
      IO.stdout.println();
      parser.print();
      System.exit(1);
    }
    // first -help
    if (parser.hasFlag("-help")) {
      Cmd.title();
      parser.print();
      System.exit(0);
    }
    // second -version
    if (parser.hasFlag("-version")) {
      IO.stdout.println(Settings.VERSION);
      System.exit(0);
    }
    // third -iset
    if (parser.hasFlag("-iset")) {
      Cmd.title();
      Globals.iset.print();
      System.exit(0);
    }
    // fourth -usage
    if (parser.hasFlag("-usage")) {
      Cmd.title();
      Globals.iset.print(parser.value("-usage"));
      System.exit(0);
    }
    // get files
    ArrayList<String> files = parser.targets();
    // find trap handler
    if (Settings.ROOT != null) {
      File trapfile = new File(Settings.ROOT + File.separator + "traphandler.s");
      if (trapfile.exists()) {
        Settings.TRAP = trapfile.getAbsolutePath();
      }
    }
    // check project mode
    if (files.size() == 1 && files.get(0).equals(".")) {
      // remove this
      files.remove(0);
      try {
        // recursively find all files in user cwd
        Files.find(
          Paths.get(System.getProperty("user.dir")),
          Integer.MAX_VALUE,
          // keep files that ends with .s or .asm extension
          (filePath, fileAttr) -> {
            if (fileAttr.isRegularFile()) {
              String path = filePath.toString();
              if (path.endsWith(".s") || path.endsWith(".asm"))
                return true;
            }
            return false;
          }
        ).forEach(
          path ->
            files.add(path.toString())
        );
      } catch (Exception e) {
        if (!Settings.QUIET)
          Message.warning("An error occurred while recursively searching the files in the directory (aborting...)");
      }
    }
    files.trimToSize();
    return files;
  }

  /**
   * This method prints the title of the V-Sim simulator.
   */
  public static void title() {
    // print the title and license note if the -notitle flag is not set
    String newline = System.getProperty("line.separator");
    if (Settings.TITLE) {
      // cool title :]
      IO.stdout.println(Colorize.yellow(" _   __") + Colorize.blue("    _____"));
      IO.stdout.println(Colorize.yellow("| | / /") + "___" + Colorize.blue("/ __(_)_ _"));
      IO.stdout.println(Colorize.yellow("| |/ /") + "___" + Colorize.blue("/\\ \\/ /  ' \\"));
      IO.stdout.println(Colorize.yellow("|___/") + Colorize.blue("   /___/_/_/_/_/") + newline);
      IO.stdout.println(Colorize.cyan("RISC-V Assembler & Runtime Simulator" + newline));
      IO.stdout.println("GPL-3.0 License");
      IO.stdout.println("Copyright (c) 2018 Andres Castellanos");
      IO.stdout.println("All Rights Reserved");
      IO.stdout.println("See the file LICENSE for a full copyright notice" + newline);
    }
    if (Settings.TRAP != null) {
      IO.stdout.println("loaded: " + Settings.TRAP + newline);
    }
  }

  /**
   * This method prints the prompt of the V-Sim simulator.
   */
  public static void prompt() {
    IO.stdout.print(Colorize.yellow(">"));
    IO.stdout.print(Colorize.blue(">"));
    IO.stdout.print(Colorize.yellow(">") + " ");
  }

}
