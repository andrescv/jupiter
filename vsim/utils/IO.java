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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import vsim.Settings;
import vsim.gui.components.InputDialog;
import vsim.gui.utils.ConsoleInput;


/** The class IO represents the standard I/O of the simulator. */
public final class IO {

  /** CLI standard input */
  public static final BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

  /** CLI standard output */
  public static PrintStream stdout = System.out;

  /** CLI standard err */
  public static PrintStream stderr = System.err;

  /** GUI standard input */
  public static ConsoleInput guistdin = null;

  /** GUI input dialog */
  public static InputDialog dialog = null;

  /**
   * Reads an integer value from current standard input. Client is responsible for catching NumberFormatException.
   *
   * @return int value corresponding to user input
   */
  public static int readInt() {
    String input = "0";
    if (Settings.GUI) {
      if (!Settings.POPUP_ECALL_INPUT && guistdin != null)
        input = IO.guistdin.readString(-1);
      else
        input = IO.dialog.getInput("Enter an integer value");
    } else {
      try {
        input = IO.stdin.readLine();
      } catch (IOException e) {
      }
    }
    // handle CTRL + D
    if (input == null) {
      IO.stdout.println();
      input = "0";
    }
    // client is responsible for catching NumberFormatException
    return Data.parseInt(input.trim());
  }

  /**
   * Reads a float value from current standard input. Client is responsible for catching NumberFormatException.
   *
   * @return float value corresponding to user input
   */
  public static float readFloat() {
    String input = "0.0";
    if (Settings.GUI) {
      if (!Settings.POPUP_ECALL_INPUT && guistdin != null)
        input = IO.guistdin.readString(-1);
      else
        input = IO.dialog.getInput("Enter a float value");
    } else {
      try {
        input = IO.stdin.readLine();
      } catch (IOException e) {
      }
    }
    // handle CTRL + D
    if (input == null) {
      IO.stdout.println();
      input = "0.0";
    }
    // client is responsible for catching NumberFormatException
    return Data.parseFloat(input.trim());
  }

  /**
   * Reads a string from current standard input.
   *
   * @param maxLength the maximum string length
   * @return the entered string, truncated to maximum length if necessary
   */
  public static String readString(int maxLength) {
    String input = "";
    if (Settings.GUI) {
      if (!Settings.POPUP_ECALL_INPUT && guistdin != null)
        input = IO.guistdin.readString(maxLength);
      else
        input = IO.dialog.getInput("Enter a string");
    } else {
      try {
        input = IO.stdin.readLine();
      } catch (IOException e) {
      }
    }
    // handle CTRL + D
    if (input == null) {
      IO.stdout.println();
      input = "";
    }
    // ensure maxLength
    if (input.length() > maxLength)
      input = (maxLength <= 0) ? "" : input.substring(0, maxLength);
    return input;
  }

  /**
   * Reads a char from current standard input.
   *
   * @return int value with lowest byte corresponding to user input
   */
  public static int readChar() {
    String input = "0";
    if (Settings.GUI) {
      if (!Settings.POPUP_ECALL_INPUT && guistdin != null)
        input = IO.guistdin.readString(1);
      else
        input = IO.dialog.getInput("Enter a character value");
    } else {
      try {
        input = IO.stdin.readLine();
      } catch (IOException e) {
      }
    }
    // handle CTRL + D
    if (input == null)
      input = "0";
    return (int) input.charAt(0);
  }
}
