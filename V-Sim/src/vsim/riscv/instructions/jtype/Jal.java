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

package vsim.riscv.instructions.jtype;

import vsim.Globals;


/**
 * The Jal class represents a jal instruction.
 */
public final class Jal extends JType {

  /**
   * Unique constructor that initializes a newly Jal instruction.
   *
   * @see vsim.riscv.instructions.jtype.JType
   */
  public Jal() {
    super("jal", "jal rd, offset", "set x[rd] = pc + 4 and pc = pc + sext(offset)");
  }

  @Override
  public int getOpCode() {
    return 0b1101111;
  }

  @Override
  protected int compute(int imm) {
    int pc = Globals.regfile.getProgramCounter();
    Globals.regfile.setProgramCounter(pc + imm);
    return pc + 4;
  }

}
