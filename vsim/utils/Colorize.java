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


/**
 * The class Colorize contains useful methods for generating strings with color.
 */
public final class Colorize {

  private Colorize() { /* NOTHING */ }

  // enable this only on linux
  private static final String  OS = System.getProperty("os.name");
  private static final boolean ENABLED = Colorize.OS.startsWith("Linux");

  // available ANSI colors
  private static final String RESET  = "\u001B[0m";
  private static final String BLACK  = "\u001B[30m";
  private static final String RED    = "\u001B[31m";
  private static final String GREEN  = "\u001B[32m";
  private static final String YELLOW = "\u001B[33m";
  private static final String BLUE   = "\u001B[34m";
  private static final String PURPLE = "\u001B[35m";
  private static final String CYAN   = "\u001B[36m";

  /**
   * This method colors a String with the given color
   *
   * @param color the ANSI color
   * @param text the string to colorize
   * @return a colored String with the given color
   */
  private static String color(String color, String text) {
    if (ENABLED && Settings.COLORIZE)
      return color + text + RESET;
    return text;
  }

  /**
   * This method colors a String with the red color.
   *
   * @param text the string to colorize
   * @return a red colored String
   */
  public static String red(String text) {
    return color(RED, text);
  }

  /**
   * This method colors a String with the green color.
   *
   * @param text the string to colorize
   * @return a green colored String
   */
  public static String green(String text) {
    return color(GREEN, text);
  }

  /**
   * This method colors a String with the yellow color.
   *
   * @param text the string to colorize
   * @return a yellow colored String
   */
  public static String yellow(String text) {
    return color(YELLOW, text);
  }

  /**
   * This method colors a String with the blue color.
   *
   * @param text the string to colorize
   * @return a blue colored String
   */
  public static String blue(String text) {
    return color(BLUE, text);
  }

  /**
   * This method colors a String with the purple color.
   *
   * @param text the string to colorize
   * @return a purple colored String
   */
  public static String purple(String text) {
    return color(PURPLE, text);
  }

  /**
   * This method colors a String with the cyan color.
   *
   * @param text the string to colorize
   * @return a cyan colored String
   */
  public static String cyan(String text) {
    return color(CYAN, text);
  }

}
