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

package vsim.riscv.instructions.itype;


/**
 * The Srai class represents a srai instruction.
 */
public final class Srai extends IType {

  /**
   * Unique constructor that initializes a newly Srai object.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Srai() {
    super(
      "srai",
      "srai rd, rs1, shamt",
      "set rd = rs1 >> shamt, arithmetic shift right"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  public int getFunct7() {
    return 0b0100000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 >> (imm & 0x1f);
  }

}
