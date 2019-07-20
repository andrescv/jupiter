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

package jupiter.exc;


/** Halt exception for exit ecall. */
public final class HaltException extends SimulationException {

  /** exit code */
  private final int code;

  /**
   * Creates a new halt exception.
   *
   * @param code exit code
   */
  public HaltException(int code) {
    super(String.format("exit(%d)", code));
    this.code = code;
  }

  /**
   * Returns exit code.
   *
   * @return exit code
   */
  public int getCode() {
    return code;
  }

}
