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
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import vsim.Flags;
import vsim.Globals;
import vsim.Logger;


/** Command line parser utility. */
public final class Cmd {

  /** unique Cmd instance */
  private static final Cmd cmd = new Cmd();

  /** current option id */
  private int id;
  /** argument parser options */
  private final HashMap<String, Boolean> opts;
  /** contains the options that are set */
  private final HashMap<Integer, String> set;
  /** maps an option to an id */
  private final HashMap<String, Integer> map;

  /** Creates a new command line argument parser. */
  private Cmd() {
    id = 0;
    opts = new HashMap<>();
    set = new HashMap<>();
    map = new HashMap<>();
    // simulator options
    add("-h", "--help", false);
    add("-v", "--version", false);
    add("--hist", true);
    add("--license", false);
    add("--docs", false);
    add("--no-title", false);
    add("-b", "--bare", false);
    add("-f", "--self", false);
    add("-e", "--extrict", false);
    add("-s", "--start", true);
    add("-g", "--debug", false);
    add("-c", "--code", true);
    add("-d", "--data", true);
    add("--format", true);
  }

  /**
   * Adds a new option that contains a short name and a long name.
   *
   * @param opt option short name
   * @param longOpt option long name
   * @param hasArg if {@code true} this option needs a value
   */
  private void add(String opt, String longOpt, boolean hasArg) {
    opts.put(opt, hasArg);
    opts.put(longOpt, hasArg);
    map.put(opt, id);
    map.put(longOpt, id);
    id++;
  }

  /**
   * Adds a new option that only contains a long name.
   *
   * @param longOpt option long name
   * @param hasArg if {@code true} this option needs a value
   */
  private void add(String longOpt, boolean hasArg) {
    add(null, longOpt, hasArg);
  }

  /**
   * Returns whether the named option is a member of this argument parser options.
   *
   * @param opt short or long name of the option
   * @return {@code true} if the named option is a member of this argument parser options, {@code false} otherwise
   */
  private boolean hasOption(String opt) {
    return map.containsKey(opt) && set.containsKey(map.get(opt));
  }

  /**
   * Retrieve the argument, if any, of this named option.
   *
   * @param opt short or long name of the option
   * @return value of the argument if option is set, and has an argument, otherwise {@code null}.
   */
  private String getOptionValue(String opt) {
    if (hasOption(opt))
      return set.get(map.get(opt));
    return null;
  }

  /**
   * Parses command line arguments according to the argument parser options.
   *
   * @param args command line arguments
   * @return a list of assembly files passed in command line arguments
   * @throws IllegalArgumentException if some command line argument is invalid
   */
  private ArrayList<Path> parseArgs(String[] args) {
    ArrayList<String> flags = new ArrayList<>();
    ArrayList<Integer> pos = new ArrayList<>();
    ArrayList<Integer> ignore = new ArrayList<>();
    ArrayList<Path> files = new ArrayList<>();
    HashMap<Integer, String> posToValue = new HashMap<>();
    // check flags and values
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("-") && opts.containsKey(args[i])) {
        flags.add(args[i]);
        pos.add(i);
        set.put(map.get(args[i]), null);
        // ignore option value position
        if (opts.get(args[i])) {
          ignore.add(i + 1);
        }
      } else if (args[i].startsWith("-")) {
        throw new IllegalArgumentException("unknown option: " + args[i]);
      } else {
        posToValue.put(i, args[i]);
      }
    }
    // verify and store values
    for (int i = 0; i < flags.size(); i++) {
      String flag = flags.get(i);
      int position = pos.get(i);
      // flag value should be at the next position
      String value = posToValue.get(position + 1);
      if (value == null && opts.containsKey(flag) && opts.get(flag)) {
        throw new IllegalArgumentException("option '" + flag + "' requires a value");
      } else if (value != null && opts.containsKey(flag) && opts.get(flag)) {
        set.put(map.get(flag), value);
      }
    }
    // set targets
    for (Integer index : posToValue.keySet()) {
      if (!ignore.contains(index)) {
        String filename = posToValue.get(index);
        Path file = Paths.get(filename);
        if (FS.isDirectory(file)) {
          for (Path path : FS.ls(file)) {
            String p = path.toString();
            if (!FS.contains(path, files) && (p.endsWith(".s") || p.endsWith(".asm"))) {
              files.add(path);
            } else {
              throw new IllegalArgumentException("passed duplicated file: " + path);
            }
          }
        } else if (FS.isRegularFile(file) && (filename.endsWith(".s") || filename.endsWith(".asm"))) {
          if (!FS.contains(file, files)) {
            files.add(file);
          } else {
            throw new IllegalArgumentException("passed duplicated file: " + filename);
          }
        } else {
          System.err.println(FS.isRegularFile(file));
          System.err.println(filename.endsWith(".s") || filename.endsWith(".asm"));
          throw new IllegalArgumentException("invalid file: " + filename);
        }
      }
    }
    files.trimToSize();
    return files;
  }

  /**
   * Parses command line arguments.
   *
   * @param args Command line arguments
   * @return A list of paths representing the assembly files (*.s or *.asm) passed.
   */
  public static ArrayList<Path> parse(String[] args) {
    try {
      // parse command line arguments
      ArrayList<Path> files = cmd.parseArgs(args);
      // set simulator flags
      Flags.TITLE = !cmd.hasOption("--no-title");
      Flags.BARE = cmd.hasOption("--bare");
      Flags.DEBUG = cmd.hasOption("--debug");
      Flags.EXTRICT = cmd.hasOption("--extrict");
      Flags.SELF_MODIFYING = cmd.hasOption("--self");
      Flags.CODE = cmd.getOptionValue("--code");
      Flags.DATA = cmd.getOptionValue("--data");
      Flags.START = cmd.hasOption("--start") ? cmd.getOptionValue("--start") : Flags.START;
      if (cmd.hasOption("--hist")) {
        try {
          Flags.HIST_SIZE = Data.atoi(cmd.getOptionValue("--hist"));
        } catch (NumberFormatException e) {
          title();
          Logger.warning("invalid history size: " + cmd.getOptionValue("--hist") + Data.EOL);
          help();
          System.exit(1);
        }
      }
      title();
      // help message
      if (cmd.hasOption("--help")) {
        help();
        System.exit(0);
      }
      // online documentation
      if (cmd.hasOption("--docs")) {
        try {
          Desktop.getDesktop().browse(new URI(Globals.HELP));
          System.exit(0);
        } catch (Exception ex) {
          Logger.warning("could not open online docs, go to: " + Globals.HELP);
          System.exit(0);
        }
      }
      // simulator version
      if (cmd.hasOption("--version")) {
        version();
        System.exit(0);
      }
      // simulator license
      if (cmd.hasOption("--license")) {
        license();
        System.exit(0);
      }
      // check file size
      if (files.size() == 0) {
        Logger.error("no RISC-V assembly files were passed" + Data.EOL);
        help();
        System.exit(1);
      }
      return files;
    } catch (IllegalArgumentException e) {
      title();
      Logger.error(e.getMessage() + Data.EOL);
      help();
      System.exit(1);
    }
    return null;
  }

  /** Prints V-Sim title. */
  private static void title() {
    if (Flags.TITLE) {
      IO.stdout().println("        _   __    _____");
      IO.stdout().println("       | | / /___/ __(_)_ _");
      IO.stdout().println("       | |/ /___/\\ \\/ /  ' \\");
      IO.stdout().println("       |___/   /___/_/_/_/_/");
      IO.stdout().println();
      IO.stdout().println("RISC-V Assembler & Runtime Simulator");
      IO.stdout().println();
    }
  }

  /** Prints V-Sim version. */
  private static void version() {
    IO.stdout().println("version: " + Globals.VERSION);
  }

  /** Prints V-Sim license. */
  private static void license() {
    IO.stdout().println("GPL-3.0 License");
    IO.stdout().println(Globals.LICENSE);
    IO.stdout().println("All Rights Reserved");
    IO.stdout().println("See https://git.io/fpcYS for a full copyright notice");
  }

  /** Prints V-Sim help message. */
  private static void help() {
    IO.stdout().println("usage: vsim [options] <files>");
    IO.stdout().println(Data.EOL + "[General Options]");
    IO.stdout().println("  -h, --help             show this help message and exit");
    IO.stdout().println("  -v, --version          show V-Sim version and exit");
    IO.stdout().println("      --license          show license and exit");
    IO.stdout().println("      --docs             open online documentation");
    IO.stdout().println("      --notitle          do not print V-Sim title");
    IO.stdout().println(Data.EOL + "[Simulator Options]");
    IO.stdout().println("  -b, --bare             bare machine (no pseudo-ops)");
    IO.stdout().println("  -f, --self             enable self-modifying code");
    IO.stdout().println("  -e, --extrict          assembler warnings are consider errors");
    IO.stdout().println("  -s, --start <label>    set global start label (default: __start)");
    IO.stdout().println("  -g, --debug            start debugger");
    IO.stdout().println("      --hist <size>      maximum number of backstep operations that can be taken");
    IO.stdout().println(Data.EOL + "[Dump Options]");
    IO.stdout().println("  -c, --code <filename>  dump machine code to a file");
    IO.stdout().println("  -d, --data <filename>  dump static data to a file");
    IO.stdout().println("      --format <format>  dump format (hex|bin) (default: hex)");
  }

}
