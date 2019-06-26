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

package vsim.exceptions;


/** Throwed when a relocation error occurs. */
public class RelocationException extends AssemblerException {

  /** relocation target */
  private final String target;

  /**
   * Creates a new assembler exception.
   *
   * @param msg assembler exception message
   */
  public RelocationException(String target) {
    super("label: '" + target + "' used but not defined");
    this.target = target;
  }

  /**
   * Returns relocation target.
   *
   * @return relocation target
   */
  public String getTarget() {
    return target;
  }

}
