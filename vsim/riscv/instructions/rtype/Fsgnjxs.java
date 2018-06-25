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
 * The Fsgnjxs class represents a fsgnjx instruction.
 */
public final class Fsgnjxs extends FRType {

  /**
   * Unique constructor that initializes a newly Fsgnjxs object.
   *
   * @see vsim.riscv.instructions.rtype.FRType
   */
  public Fsgnjxs() {
    super(
      "fsgnjx.s",
      "fsgnjx.s frd, frs1, frs2",
      "set frd = {frs1[31] ^ frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  public int getFunct5() {
    return 0b00100;
  }

  @Override
  public float compute(float rs1, float rs2) {
    int ax = Float.floatToIntBits(rs1);
    int bx = Float.floatToIntBits(rs2);
    int sign = (ax ^ bx) & Data.SIGN_MASK;
    int cx = ax & (Data.EXPONENT_MASK | Data.FRACTION_MASK);
    return Float.intBitsToFloat(sign | cx);
  }

}
