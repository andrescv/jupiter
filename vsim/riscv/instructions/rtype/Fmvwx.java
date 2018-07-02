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

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The Fmvwx class represents a {@code fmv.w.x} instruction.
 */
public final class Fmvwx extends Instruction {

  /**
   * Unique constructor that initializes a newly Fmvwx instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fmvwx() {
    super(
      Format.R,
      "fmv.w.x",
      "fmv.w.x rd, rs1",
      "set f[rd] = x[rs1][31:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct7() {
    return 0b1111000;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegisterInt(
      code.get(InstructionField.RD),
      Globals.regfile.getRegister(code.get(InstructionField.RS1))
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return Colorize.cyan(String.format("%s f%d, x%d", op, rd, rs1));
  }

}
