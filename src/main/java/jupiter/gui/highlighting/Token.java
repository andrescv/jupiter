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

package jupiter.gui.highlighting;


/** GUI text editor syntax highlighting token representation. */
public final class Token {

  /** class/style name */
  private String style;
  /** style span length */
  private int length;

  /**
   * Creates a new token given a class/style name and the length.
   *
   * @param style class/style name
   * @param length token length
   */
  public Token(String style, int length) {
    this.style = style;
    this.length = length;
  }

  /**
   * Returns the token style/class.
   *
   * @return token style/class
   */
  public String getStyle() {
    return this.style;
  }

  /**
   * Returns the token length.
   *
   * @return token length
   */
  public int getLength() {
    return this.length;
  }

}
