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

package vsim.asm;


/** Assembler symbol. */
public final class Symbol {

  /** symbol memory segment */
  private final Segment segment;
  /** symbol address */
  private int address;

  /**
   * Creates a new assembler symbol.
   *
   * @param segment memory segment
   * @param address symbol address
   */
  public Symbol(Segment segment, int address) {
    this.segment = segment;
    this.address = address;
  }

  /**
   * Sets symbol address.
   *
   * @param address new address of the symbol
   */
  public void setAddress(int newAddress) {
    address = newAddress;
  }

  /**
   * Returns symbol segment.
   *
   * @return symbol segment
   */
  public Segment getSegment() {
    return segment;
  }

  /**
   * Returns symbol address.
   *
   * @return symbol address
   */
  public int getAddress() {
    return address;
  }

}
