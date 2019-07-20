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


/** Throwed when attempting to read or write to an invalid memory address. */
public final class InvalidAddressException extends SimulationException {

  /**
   * Creates a new invalid address exception.
   *
   * @param address memory address
   * @param read true if exception occurs while reading or false while writing
   */
  public InvalidAddressException(int address, boolean read) {
    super(String.format("attempting to %s to an invalid memory address 0x%08x", read ? "read" : "write", address));
  }

}
