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

package jvpiter.utils.io;

import java.io.PrintStream;


/** CLI output. */
public final class CLIConsoleOutput implements Output {

  /** PrintStream instance */
  private final PrintStream out;

  /**
   * Creates a new command line output from a print stream.
   *
   * @param ps PrintStream (e.g System.out, System.err)
   */
  public CLIConsoleOutput(PrintStream ps) {
    out = ps;
  }

  /** {@inheritDoc} */
  @Override
  public void print(String txt) {
    out.print(txt);
  }

  /** {@inheritDoc} */
  @Override
  public void println(String txt) {
    out.println(txt);
  }

  /** {@inheritDoc} */
  @Override
  public void println() {
    out.println();
  }

}
