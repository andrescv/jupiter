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

package vsim.riscv.instructions.utype;


/**
 * The Lui class represents a lui instruction.
 */
public final class Lui extends UType {

  /**
   * Unique constructor that initializes a newly Lui instruction.
   *
   * @see vsim.riscv.instructions.utype.UType
   */
  public Lui() {
    super(
      "lui",
      "lui rd, imm",
      "set x[rd] = imm << 12"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0110111;
  }

  @Override
  protected int compute(int imm) {
    return imm << 12;
  }

}
