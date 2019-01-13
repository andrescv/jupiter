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
 * The Andi class represents an andi instruction.
 */
public final class Andi extends IType {

  /**
   * Unique constructor that initializes a newly Andi instruction.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Andi() {
    super("andi", "andi rd, rs1, imm", "set x[rd] = x[rs1] & sext(imm), bitwise and");
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 & imm;
  }

}
