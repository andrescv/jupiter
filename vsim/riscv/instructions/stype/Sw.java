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

package vsim.riscv.instructions.stype;

import vsim.Globals;


/**
 * The Sw class represents a sw instruction.
 */
public final class Sw extends SType {

  /**
   * Unique constructor that initializes a newly Sw object.
   *
   * @see vsim.riscv.instructions.stype.SType
   */
  public Sw() {
    super(
      "sw",
      "sw rs2, offset(rs1)",
      "set memory[rs1 + sext(offset)] = rs2[31:0]"
    );
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    Globals.memory.storeWord(rs1 + imm, rs2);
  }

}
