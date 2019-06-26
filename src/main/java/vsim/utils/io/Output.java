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

package vsim.utils.io;


/** User output interface. */
public interface Output {

  /**
   * Prints a String.
   *
   * @param txt String to print
   */
  public void print(String txt);

  /**
   * Prints a String and then terminate the line.
   *
   * @param txt String to print.
   */
  public void println(String txt);

  /** Terminates the current line by writing the line separator string. */
  public void println();

}
