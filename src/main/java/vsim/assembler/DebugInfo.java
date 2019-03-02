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

package vsim.assembler;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;


/**
 * The class DebugInfo encapsulates useful debug information.
 */
public final class DebugInfo {

  /** the line number */
  private int lineno;
  /** the column number */
  private int column;
  /** the source line */
  private String source;
  /** the filename attached to this debug info */
  private String filename;

  /**
   * Unique constructor that initializes a new debug information.
   *
   * @param lineno the line number of this debug information
   * @param column the column number of this debug information
   * @param source the source line of this debug information
   * @param filename the filename of this debug information
   */
  public DebugInfo(int lineno, int column, String source, String filename) {
    this.lineno = lineno;
    this.column = column;
    this.source = source;
    this.filename = filename;
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
   * Gets the column number of the debug information.
   *
   * @return column number
   */
  public int getColumnNumber() {
    return this.column;
  }

  /**
   * This method returns the source line of the debug information.
   *
   * @return the source line
   */
  public String getSource() {
    try {
      // try to get original source line always
      // this is only because inside parser some grammar derivations
      // could not construct properly the original source line
      Stream<String> lines = Files.lines(Paths.get(filename));
      return lines.skip(lineno - 1).findFirst().get();
    } catch (Exception e) {
      // if some exception happens, use the
      // constructed source line in the parser
      return this.source;
    }
  }

  /**
   * This method returns the filename of the debug information.
   *
   * @return the filename
   */
  public String getFilename() {
    return this.filename;
  }

}
