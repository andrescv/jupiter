package vsim.utils;

import vsim.Settings;
import java.util.ArrayList;


public final class Cmd {

  // newline
  private static final String newline = System.getProperty("line.separator");

  // VSim header
  private static final String HEADER = Colorize.red("__   _____ _") + newline +
                                       Colorize.green("\\ \\ / / __(_)_ ___") + newline +
                                       Colorize.blue(" \\ V /\\__ \\ | '   \\") + newline +
                                       Colorize.yellow("  \\_/ |___/_|_|_|_|") + newline;

  // slogan and license
  private static final String SUBHEADER = Colorize.cyan("RISC-V Assembler & Runtime Simulator") + newline +
                                          newline + "MIT License" + newline +
                                          "Copyright (c) 2018 Andres Castellanos" + newline +
                                          "All Rights Reserved." + newline +
                                          "See the file README for a full copyright notice." + newline;

  // usage [-h]
  private static final String USAGE = "usage: vsim [flags] <files>" +
                                      newline + newline + "optional flags:" + newline +
                                      "  -h        show this help message and exit" + newline +
                                      "  -asm      extended machine (pseudo-ops) (default)" + newline +
                                      "  -bare     bare machine (no pseudo-ops)" + newline +
                                      "  -quiet    do not print warnings" + newline +
                                      "  -noquiet  print warnings (default)" + newline +
                                      "  -nocolor  dont colorize output (only on linux)" + newline +
                                      "  -color    colorize output (only on linux) (default)" + newline +
                                      "  -debug    debug program";

  public static ArrayList<String> parse(String[] args) {
    int lastArg = 0;
    int firstFile = Math.max(0, args.length - 1);
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("-")) {
        lastArg = i;
        String option = args[i].substring(1);
        if (option.equals("h"))
          Cmd.exit();
        else if (option.equals("asm"))
          Settings.BARE = false;
        else if (option.equals("bare"))
          Settings.BARE = true;
        else if (option.equals("quiet"))
          Settings.QUIET = true;
        else if (option.equals("noquiet"))
          Settings.QUIET = false;
        else if (option.equals("nocolor"))
          Settings.COLORIZE = false;
        else if (option.equals("color"))
          Settings.COLORIZE = true;
        else if (option.equals("debug"))
          Settings.DEBUG = true;
        else {
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
    if (firstFile != lastArg) {
      for (int i = firstFile; i < args.length; i++) {
        files.add(args[i]);
      }
    }
    files.trimToSize();
    return files;
  }

  public static void title() {
    System.out.println(Cmd.HEADER);
    System.out.println(Cmd.SUBHEADER);
  }

  public static void prompt() {
    System.out.print(Colorize.red(">") + Colorize.green(">") + Colorize.blue("> "));
  }

  public static void exit() {
    System.out.println(Cmd.USAGE);
    System.exit(0);
  }

}
