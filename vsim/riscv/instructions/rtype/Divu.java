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


/**
 * The Divu class represents a divu instruction.
 */
public final class Divu extends RType {

  /**
   * Unique constructor that initializes a newly Divu object.
   *
   * @see vsim.riscv.instructions.rtype.RType
   */
  public Divu() {
    super(
      "divu",
      "divu rd, rs1, rs2",
      "set rd = rs1 unsigned(/) rs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    if (rs2 != 0)
      return Integer.divideUnsigned(rs1, rs2);
    return 0xffffffff;
  }

}
