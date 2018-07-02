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
 * The Fcvtwus class represents a {@code fcvt.wu.s} instruction.
 */
public final class Fcvtwus extends Instruction {

  /**
   * Unique constructor that initializes a newly Fcvtwus instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fcvtwus() {
    super(
      Format.R,
      "fcvt.wu.s",
      "fcvt.wu.s rd, rs1",
      "set x[rd] = (int)(unsigned(f[rs1]))"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct7() {
    return 0b1100000;
  }

  @Override
  public void execute(MachineCode code) {
    float value = Globals.fregfile.getRegister(code.get(InstructionField.RS1));
    int result;
    if (value < Integer.MIN_VALUE)
      result = 0;
    else if (value > Integer.MAX_VALUE || Float.isNaN(value))
      result = Integer.MAX_VALUE;
    else
      result = Math.round(value);
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      result
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return Colorize.cyan(String.format("%s x%d, f%d", op, rd, rs1));
  }

}
