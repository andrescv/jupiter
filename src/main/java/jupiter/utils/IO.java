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

package jupiter.utils;

import jupiter.utils.io.CLIConsoleInput;
import jupiter.utils.io.CLIConsoleOutput;
import jupiter.utils.io.Input;
import jupiter.utils.io.Output;


/** Standard input/output. */
public final class IO {

  /** command line standard input */
  private static Input stdin = new CLIConsoleInput(System.in);
  /** command line standard output */
  private static Output stdout = new CLIConsoleOutput(System.out);
  /** command line standard error */
  private static Output stderr = new CLIConsoleOutput(System.err);

  /**
   * Sets standard input.
   *
   * @param stdin new standard input;
   */
  public static void setStdin(Input stdin) {
    IO.stdin = stdin;
  }

  /**
   * Sets standard output.
   *
   * @param stdout new standard output;
   */
  public static void setStdout(Output stdout) {
    IO.stdout = stdout;
  }

  /**
   * Sets standard error.
   *
   * @param stdout new standard error;
   */
  public static void setStderr(Output stderr) {
    IO.stderr = stderr;
  }

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
