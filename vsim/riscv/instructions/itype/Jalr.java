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

import vsim.Globals;


/**
 * The Jalr class represents a jalr instruction.
 */
public final class Jalr extends IType {

  /**
   * Unique constructor that initializes a newly Jalr instruction.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Jalr() {
    super(
      "jalr",
      "jalr rd, offset",
      "set x[rd] = pc + 4 and pc = pc + ((x[rs1] + sext(offset)) & ~1)"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1100111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    int pc = Globals.regfile.getProgramCounter();
    // set the least-significant bit of the result to zero
    Globals.regfile.setProgramCounter((rs1 + imm) & ~1);
    return pc + 4;
  }

}
