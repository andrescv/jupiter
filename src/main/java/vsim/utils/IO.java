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

import vsim.utils.io.CmdInput;
import vsim.utils.io.CmdOutput;
import vsim.utils.io.Input;
import vsim.utils.io.Output;


/** Standard input/output. */
public final class IO {

  /** command line standard input */
  private static final Input stdin = new CmdInput(System.in);
  /** command line standard output */
  private static final Output stdout = new CmdOutput(System.out);
  /** command line standard error */
  private static final Output stderr = new CmdOutput(System.err);

  /**
   * Returns current standard input.
   *
   * @return current standard input
   */
  public static Input stdin() {
    return stdin;
  }

  /**
   * Returns current standard output.
   *
   * @return current standard output
   */
  public static Output stdout() {
    return stdout;
  }

  /**
   * Returns current standard error.
   *
   * @return Current standard error
   */
  public static Output stderr() {
    return stderr;
  }

  /**
   * Reads an {@code int} value from current standard input.
   *
   * @return {@code int} value corresponding to user input
   * @throws NumberFormatException if the input does not contain a parsable int
   */
  public static int readInt() {
    return Data.atoi(stdin().readLine());
  }

  /**
   * Reads a {@code float} value from current standard input.
   *
   * @return {@code float} value corresponding to user input
   * @throws NumberFormatException If the input is invalid
   */
  public static float readFloat() {
    return Data.atof(stdin().readLine());
  }

  /**
   * Reads a String from current standard input.
   *
   * @param maxLength The maximum String length
   * @return The entered String, truncated to maximum length if necessary
   */
  public static String readString(int maxLength) {
    String input = stdin().readLine();
    if (input.length() > maxLength)
      input = input.substring(0, Math.max(0, maxLength));
    return input;
  }

  /**
   * Reads a character from current standard input.
   *
   * @return {@code int} value corresponding to the first byte of user input
   */
  public static int readChar() {
    return stdin().read();
  }

}
