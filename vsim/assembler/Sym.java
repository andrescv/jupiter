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

import vsim.utils.Colorize;


/**
 * The class Sym is used to represent a segment symbol.
 */
final class Sym {

  /** the segment this symbol belongs */
  private Segment segment;
  /** the address attached to this symbol */
  private int address;

  /**
   * Unique constructor that initializes a newly Sym object.
   *
   * @param segment the segment this symbol belongs
   * @param address the address attached to this symbol
   * @see vsim.assembler.Segment
   */
  protected Sym(Segment segment, int address) {
    this.segment = segment;
    this.address = address;
  }

  /**
   * This method sets the address of this symbol.
   *
   * @param address the address to set
   */
  protected void setAddress(int address) {
    this.address = address;
  }

  /**
   * This method returns the segment this symbol belongs.
   *
   * @see vsim.assembler.Segment
   * @return the segment this symbol belongs
   */
  protected Segment getSegment() {
    return this.segment;
  }

  /**
   * This method returns the address of this symbol.
   *
   * @return the address of this symbol
   */
  protected int getAddress() {
    return this.address;
  }

  /**
   * This method returns a String representation of a Sym object.
   *
   * @return the String representation
   */
  @Override
  public String toString() {
    return String.format(
      "[%s] @ %s",
      Colorize.green(this.segment.toString().toLowerCase()),
      Colorize.blue(String.format("0x%08x", this.address))
    );
  }

}
