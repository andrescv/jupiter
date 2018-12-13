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
import java.io.IOException;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;


/**
 * The class IO represents the standard I/O of the simulator.
 */
public final class IO {

  /** standard input */
  public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

  /** standard output */
  public static PrintStream stdout = System.out;

  /** standard err */
  public static PrintStream stderr = System.err;

}
