package vsim.utils;


public final class Colorize {

  // enable color output only on linux
  private static final String  OS = System.getProperty("os.name");
  private static final boolean ENABLED = Colorize.OS.startsWith("Linux");

  // ansi colors
  private static final String RESET  = "\u001B[0m";
  private static final String BLACK  = "\u001B[30m";
  private static final String RED    = "\u001B[31m";
  private static final String GREEN  = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE   = "\u001B[34m";
  private static final String PURPLE = "\u001B[35m";
  private static final String CYAN   = "\u001B[36m";

  private static String color(String color, String text) {
    if (ENABLED)
      return color + text + RESET;
    return text;
  }

  public static String red(String text) {
    return color(RED, text);
  }

  public static String green(String text) {
    return color(GREEN, text);
  }

  public static String yellow(String text) {
    return color(YELLOW, text);
  }

  public static String blue(String text) {
    return color(BLUE, text);
  }

  public static String purple(String text) {
    return color(PURPLE, text);
  }

  public static String cyan(String text) {
    return color(CYAN, text);
  }

}
