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

package vsim.assembler;


/**
 * The class DebugInfo encapsulates useful debug information.
 */
public final class DebugInfo {

  /** the line number */
  private int lineno;
  /** the source line */
  private String source;

  /**
   * Unique constructor that initializes a newly DebugInfo object.
   *
   * @param lineno the line number of this debug information
   * @param source the source line of this debug information
   */
  public DebugInfo(int lineno, String source) {
    this.lineno = lineno;
    this.source = source;
  }

  /**
   * This method returns the line number of the debug information.
   *
   * @return the line number
   */
  public int getLineNumber() {
    return this.lineno;
  }

  /**
   * This method returns the source line of the debug information.
   *
   * @return the source line
   */
  public String getSource() {
    return this.source;
  }

}
