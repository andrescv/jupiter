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

package jupiter;

import jupiter.utils.IO;


/** Jupiter log messages. */
public final class Logger {

  /**
   * Prints a message to stdout.
   *
   * @param msg Message to print
   */
  public static void info(String msg) {
    IO.stdout().println("Jupiter: " + msg);
  }

  /**
   * Prints a warning message to stdout.
   *
   * @param msg Warning message to print
   */
  public static void warning(String msg) {
    IO.stdout().println("Jupiter: (warning) " + msg);
  }

  /**
   * Prints an error message to stderr.
   *
   * @param msg Error message to print
   */
  public static void error(String msg) {
    IO.stderr().println("Jupiter: (error) " + msg);
  }

}
