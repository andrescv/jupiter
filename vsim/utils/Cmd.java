package vsim.utils;

import vsim.Settings;
import java.util.ArrayList;
import java.io.StringReader;
import java.io.BufferedReader;


public final class Cmd {

  // newline
  private static final String newline = System.getProperty("line.separator");

  // VSim header
  private static final String HEADER = "__   _____ _" + newline +
                                       "\\ \\ / / __(_)_ ___" + newline +
                                       " \\ V /\\__ \\ | '   \\" + newline +
                                       "  \\_/ |___/_|_|_|_|";

  // slogan and license
  private static final String SUBHEADER = "RISC-V Assembler & Runtime Simulator" + newline +
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
    for (int i = firstFile; i < args.length; i++) {
      if (!args[i].startsWith("-"))
        files.add(args[i]);
    }
    files.trimToSize();
    return files;
  }

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

  public static void prompt() {
    System.out.print(Colorize.red(">") + Colorize.green(">") + Colorize.blue("> "));
  }

  public static void exit() {
    System.out.println(Cmd.USAGE);
    System.exit(0);
  }

}
