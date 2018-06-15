package vsim.utils;

import vsim.Globals;
import java.util.ArrayList;


public final class Message {

  public static void log(String msg) {
    System.out.println(Colorize.cyan("vsim: " + msg));
  }

  public static void warning(String msg) {
    System.out.println(Colorize.yellow("vsim: (warning) " + msg));
  }

  public static void error(String msg) {
    System.err.println(Colorize.red("vsim: (error) " + msg));
  }

  public static void panic(String msg) {
    System.err.println(Colorize.red("vsim: (error) " + msg));
    System.exit(-1);
  }

  public static void errors() {
    if (Globals.errors.size() > 0) {
      for (String msg: Globals.errors)
        Message.error(msg);
      System.out.println(Globals.errors.size() + " error(s)");
      System.exit(-1);
    }
  }

}
