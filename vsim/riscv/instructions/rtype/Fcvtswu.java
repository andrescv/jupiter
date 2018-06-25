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
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The Fcvtswu class represents a fcvtswu instruction.
 */
public final class Fcvtswu extends Instruction {

  /**
   * Unique constructor that initializes a newly Fcvtswu object.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fcvtswu() {
    super(
      Instruction.Format.R,
      "fcvt.s.wu",
      "fcvt.s.wu frd, rs1",
      "set frd = (float)(unsigned(rs1))"
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
  public int getFunct5() {
    return 0b11010;
  }

  @Override
  public void execute(MachineCode code) {
    int value = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      ((Long) Integer.toUnsignedLong(value)).floatValue()
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
