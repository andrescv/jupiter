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
import java.io.IOException;
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
  public static ArrayList<File> parse(String[] args) {
    ArgumentParser parser = new ArgumentParser("vsim [options] <files>");
    // simulator available options
    parser.add("-help",    "show this help message and exit");
    parser.add("-all",     "assemble all files in directory");
    parser.add("-bare",    "bare machine (no pseudo-ops)");
    parser.add("-quiet",   "do not print warnings");
    parser.add("-extrict", "assembler warnings are consider errors");
    parser.add("-nocolor", "do not colorize output");
    parser.add("-usage",   "print usage of an instruction and exit", "<mnemonic>");
    parser.add("-notitle", "do not print V-Sim title");
    parser.add("-dump",    "dump machine code to a file", "<file>");
    parser.add("-start",   "start program at global label (default: main)", "<label>");
    parser.add("-debug",   "start the debugger");
    parser.add("-version", "show the simulator version and exit");
    parser.add("-license", "show license and copyright notice and exit");
    parser.add("-iset",    "print available RISC-V instructions and exit");
    // parse args
    parser.parse(args);
    // display usage if errors
    if (parser.hasErrors()) {
      Cmd.title();
      for (String error: parser.getErrors())
        Message.warning(error);
      IO.stdout.println();
      parser.print();
      System.exit(1);
    }
    // override default Settings
    Settings.BARE = parser.hasFlag("-bare");
    Settings.QUIET = parser.hasFlag("-quiet");
    Settings.EXTRICT = parser.hasFlag("-extrict");
    Settings.COLORIZE = !parser.hasFlag("-nocolor");
    Settings.TITLE = !parser.hasFlag("-notitle");
    Settings.DUMP = parser.hasFlag("-dump") ? parser.value("-dump") : null;
    Settings.START = parser.hasFlag("-start") ? parser.value("-start") : "main";
    Settings.DEBUG = parser.hasFlag("-debug");
    // check -help flag
    if (parser.hasFlag("-help")) {
      Cmd.title();
      parser.print();
      System.exit(0);
    }
    // check -license flag
    if (parser.hasFlag("-license")) {
      Cmd.titleAndLicense();
      System.exit(0);
    }
    // check -version flag
    if (parser.hasFlag("-version")) {
      Cmd.title();
      IO.stdout.println("Version: " + Settings.VERSION);
      System.exit(0);
    }
    // check -iset flag
    if (parser.hasFlag("-iset")) {
      Cmd.title();
      Globals.iset.print();
      System.exit(0);
    }
    // check -usage flag
    if (parser.hasFlag("-usage")) {
      Cmd.title();
      Globals.iset.print(parser.value("-usage"));
      System.exit(0);
    }
    // get files
    ArrayList<File> files = parser.targets();
    // find trap handler
    if (Settings.ROOT != null) {
      File trapfile = new File(Settings.ROOT + File.separator + "traphandler.s");
      if (trapfile.exists()) {
        Settings.TRAP = trapfile;
      }
    }
    // assemble all files in directory
    if (parser.hasFlag("-all"))
      Cmd.getFilesInDir(files);
    files.trimToSize();
    return files;
  }


  /**
   * This method adds all assembler files in current user directory into an array list.
   *
   * @param files array list where file paths will be added
   * @return true if success, false otherwise
   */
  public static boolean getFilesInDir(ArrayList<File> files) {
    try {
      // recursively find all files in user cwd
      Files.find(
        Paths.get(Settings.DIR.toString()),
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
          files.add(new File(path.toString()))
      );
    } catch (IOException e) {
      Message.error("An error occurred while recursively searching the files in directory (aborting...)");
      if (!Settings.GUI)
        System.exit(1);
      return false;
    }
    return true;
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
    }
    if (Settings.TRAP != null) {
      IO.stdout.println("loaded: " + Colorize.green("traphandler.s") + newline);
    }
  }

  /**
   * This method prints the title and license of the V-Sim simulator.
   */
  public static void titleAndLicense() {
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
      IO.stdout.println("See " + Colorize.green("https://git.io/fpcYS") + " for a full copyright notice");
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
