package vsim.utils;


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

}
