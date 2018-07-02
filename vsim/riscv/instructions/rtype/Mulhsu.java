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

package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


/**
 * The Mulhsu class represents a mulhsu instruction.
 */
public final class Mulhsu extends RType {

  /**
   * Unique constructor that initializes a newly Mulhsu instruction.
   *
   * @see vsim.riscv.instructions.rtype.RType
   */
  public Mulhsu() {
    super(
      "mulhsu",
      "mulhsu rd, rs1, rs2",
      "set x[rd] = (x[rs1] * unsigned(x[rs2])) >> XLEN"
    );
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    long result = ((long) rs1) * Integer.toUnsignedLong(rs2);
    return (int)(result >> Data.WORD_LENGTH_BITS);
  }

}
