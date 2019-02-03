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

package vsim.utils;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import vsim.Globals;
import vsim.Settings;


/**
 * The class Cmd implements a basic command line interface.
 */
public final class Cmd {

  /**
   * This method parses the command line arguments and returns the source filenames passed.
   *
   * @param args arguments to parse
   * @see vsim.utils.ArgumentParser
   * @return an array of RISC-V assembler filenames to simulate (if any)
   */
  public static ArrayList<File> parse(String[] args) {
    ArgumentParser parser = new ArgumentParser("vsim [options] <files>");
    // simulator available options
    parser.add("-help", "show this help message and exit");
    parser.add("-docs", "open online V-Sim docs");
    parser.add("-all", "assemble all files in directory");
    parser.add("-bare", "bare machine (no pseudo-ops)");
    parser.add("-self", "enable self-modifying code");
    parser.add("-extrict", "assembler warnings are consider errors");
    parser.add("-info", "print info of an instruction and exit", "<mnemonic>");
    parser.add("-notitle", "do not print V-Sim title");
    parser.add("-code", "dump machine code to a file", "<file>");
    parser.add("-data", "dump static data to a file", "<file>");
    parser.add("-start", "start program at global label (default: main)", "<label>");
    parser.add("-debug", "start the debugger");
    parser.add("-version", "show the simulator version and exit");
    parser.add("-license", "show license and copyright notice and exit");
    parser.add("-trap", "load a trap handler file", "<traphandler>");
    parser.add("-iset", "print available RISC-V instructions and exit");
    // parse args
    parser.parse(args);
    // display usage if errors
    if (parser.hasErrors()) {
      Cmd.title();
      for (String error : parser.getErrors())
        Message.warning(error);
      IO.stdout.println();
      parser.print();
      System.exit(1);
    }
    // override default Settings
    Settings.BARE = parser.hasFlag("-bare");
    Settings.EXTRICT = parser.hasFlag("-extrict");
    Settings.SELF_MODIFYING = parser.hasFlag("-self");
    Settings.TITLE = !parser.hasFlag("-notitle");
    Settings.CODE = parser.hasFlag("-code") ? parser.value("-code") : null;
    Settings.DATA = parser.hasFlag("-data") ? parser.value("-data") : null;
    Settings.DEBUG = parser.hasFlag("-debug");
    // try to set start label
    if (parser.hasFlag("-start") && !Settings.setStart(parser.value("-start"), false)) {
      Cmd.title();
      Message.error("invalid start label: " + parser.value("-start"));
      System.exit(1);
    }
    // try to set trap handler
    if (parser.hasFlag("-trap")) {
      String traphandler = parser.value("-trap");
      if (!(traphandler.endsWith(".s") || traphandler.endsWith(".asm"))) {
        Cmd.title();
        Message.error("invalid trap handler extension expected .s or .asm (input: " + traphandler + ")");
        System.exit(1);
      }
      Settings.TRAP = new File(traphandler);
      if (!Settings.TRAP.exists()) {
        Settings.TRAP = null;
        Cmd.title();
        Message.error("invalid trap handler '" + traphandler + "' file does not exists");
        System.exit(1);
      }
    }
    // check -help flag
    if (parser.hasFlag("-help")) {
      Cmd.title();
      parser.print();
      System.exit(0);
    }
    // check -docs flag
    if (parser.hasFlag("-docs")) {
      try {
        Desktop.getDesktop().browse(new URI(Globals.HELP));
        System.exit(0);
      } catch (Exception ex) {
        Cmd.title();
        Message.error("could not open online docs, go to: " + Globals.HELP);
        System.exit(1);
      }
    }
    // check -license flag
    if (parser.hasFlag("-license")) {
      Cmd.titleAndLicense();
      System.exit(0);
    }
    // check -version flag
    if (parser.hasFlag("-version")) {
      Cmd.title();
      IO.stdout.println("Version: " + Globals.VERSION);
      System.exit(0);
    }
    // check -iset flag
    if (parser.hasFlag("-iset")) {
      Cmd.title();
      Globals.iset.print();
      System.exit(0);
    }
    // check -info flag
    if (parser.hasFlag("-info")) {
      Cmd.title();
      Globals.iset.print(parser.value("-info"));
      System.exit(0);
    }
    // get files
    ArrayList<File> files = parser.targets();
    // check files
    for (File f : files) {
      if (!f.exists()) {
        Cmd.title();
        Message.error("file '" + f + "' does not exists");
        System.exit(1);
      }
      if (!(f.getName().endsWith(".s") || f.getName().endsWith(".asm"))) {
        Cmd.title();
        Message.error("invalid file extension expected .s or .asm (cause: " + f + ")");
        System.exit(1);
      }
    }
    // assemble all files in directory
    if (parser.hasFlag("-all"))
      Cmd.getFilesInDir(files);
    // add traphandler
    Cmd.addTrapHandler(files);
    // check if no files passed
    if (files.isEmpty()) {
      Cmd.title();
      Message.panic("no RISC-V files passed");
    }
    files.trimToSize();
    return files;
  }

  /**
   * Adds trap handler to file list.
   *
   * @param files array list where trap handler file will be added
   */
  public static void addTrapHandler(ArrayList<File> files) {
    if (files.size() > 0) {
      // load traphandler from V-Sim install dir
      if (Settings.TRAP == null) {
        try {
          String root = new File(Cmd.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
          File trapfile = new File(root + File.separator + "traphandler.s");
          if (trapfile.exists())
            Settings.TRAP = trapfile;
        } catch (Exception e) {
          /* NOTHING */
        }
      }
      // add trap handler at the head of the list
      if (Settings.TRAP != null)
        files.add(0, Settings.TRAP);
    }
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
      Files.find(Paths.get(Settings.DIR.toString()), Integer.MAX_VALUE,
          // keep files that ends with .s or .asm extension
          (filePath, fileAttr) -> {
            if (fileAttr.isRegularFile()) {
              String path = filePath.toString();
              if (path.endsWith(".s") || path.endsWith(".asm"))
                return true;
            }
            return false;
          }).forEach(path -> {
            File f = new File(path.toString());
            // avoid duplicated files
            if (!files.contains(f))
              files.add(f);
          });
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
    if (Settings.TITLE) {
      // cool title :]
      IO.stdout.println();
      IO.stdout.println(" ██╗   ██╗      ███████╗██╗███╗   ███╗");
      IO.stdout.println(" ██║   ██║      ██╔════╝██║████╗ ████║");
      IO.stdout.println(" ██║   ██║█████╗███████╗██║██╔████╔██║");
      IO.stdout.println(" ╚██╗ ██╔╝╚════╝╚════██║██║██║╚██╔╝██║");
      IO.stdout.println("  ╚████╔╝       ███████║██║██║ ╚═╝ ██║");
      IO.stdout.println("   ╚═══╝        ╚══════╝╚═╝╚═╝     ╚═╝");
      IO.stdout.println();
      IO.stdout.println(" RISC-V Assembler & Runtime Simulator");
      IO.stdout.println();
    }
    if (Settings.TRAP != null) {
      IO.stdout.println("loaded: " + Settings.TRAP.toString());
      IO.stdout.println();
    }
  }

  /**
   * This method prints the title and license of the V-Sim simulator.
   */
  public static void titleAndLicense() {
    // cool title :]
    IO.stdout.println();
    IO.stdout.println(" ██╗   ██╗      ███████╗██╗███╗   ███╗");
    IO.stdout.println(" ██║   ██║      ██╔════╝██║████╗ ████║");
    IO.stdout.println(" ██║   ██║█████╗███████╗██║██╔████╔██║");
    IO.stdout.println(" ╚██╗ ██╔╝╚════╝╚════██║██║██║╚██╔╝██║");
    IO.stdout.println("  ╚████╔╝       ███████║██║██║ ╚═╝ ██║");
    IO.stdout.println("   ╚═══╝        ╚══════╝╚═╝╚═╝     ╚═╝");
    IO.stdout.println();
    IO.stdout.println(" RISC-V Assembler & Runtime Simulator");
    IO.stdout.println();
    IO.stdout.println("GPL-3.0 License");
    IO.stdout.println(Globals.COPYRIGHT);
    IO.stdout.println("All Rights Reserved");
    IO.stdout.println("See https://git.io/fpcYS for a full copyright notice");
  }

}
