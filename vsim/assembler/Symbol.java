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

import vsim.utils.IO;
import vsim.utils.Colorize;


/**
 * The class Symbol is used to represent a symbol of a memory segment.
 */
public final class Symbol {

  /** the segment the symbol belongs */
  private Segment segment;
  /** the address attached to the symbol */
  private int address;

  /**
   * Unique constructor that initializes a new symbol given
   * a segment and an address.
   *
   * @param segment the segment the symbol belongs
   * @param address the address attached to the symbol
   * @see vsim.assembler.Segment
   */
  public Symbol(Segment segment, int address) {
    this.segment = segment;
    this.address = address;
  }

  /**
   * This method pretty prints the symbol.
   */
  public void print() {
    IO.stdout.println(
      String.format(
        "[%s] @ %s",
        Colorize.green(this.segment.toString().toLowerCase()),
        Colorize.blue(String.format("0x%08x", this.address))
      )
    );
  }

  /**
   * This method sets the address of the symbol.
   *
   * @param address the new address of the symbol
   */
  public void setAddress(int address) {
    this.address = address;
  }

  /**
   * This method returns the segment the symbol belongs.
   *
   * @see vsim.assembler.Segment
   * @return the segment the symbol belongs
   */
  public Segment getSegment() {
    return this.segment;
  }

  /**
   * This method returns the address attached to the symbol.
   *
   * @return the address of the symbol
   */
  public int getAddress() {
    return this.address;
  }

}
