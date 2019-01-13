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

package vsim.riscv.instructions.itype;

/**
 * The Sltiu class represents a sltiu instruction.
 */
public final class Sltiu extends IType {

  /**
   * Unique constructor that initializes a newly Sltiu instruction.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Sltiu() {
    super("sltiu", "sltiu rd, rs1, imm", "set x[rd] = 1 if x[rs1] < sext(imm) else 0, unsigned comparison");
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0;
  }

}
