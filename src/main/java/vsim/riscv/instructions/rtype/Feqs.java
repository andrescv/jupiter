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

package vsim.riscv.instructions.rtype;

import vsim.State;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;


/** RISC-V feq.s (Floating-point Equal, Single-Precision) instruction. */
public final class Feqs extends Instruction {

  /** Creates a new feq.s instruction. */
  public Feqs() {
    super(Format.R, "feq.s");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b010;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct7() {
    return 0b1010000;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    int result = (rs1 == rs2) ? 1 : 0;
    state.xregfile().setRegister(code.get(InstructionField.RD), result);
    state.xregfile().incProgramCounter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return String.format("%s x%d, f%d, f%d", op, rd, rs1, rs2);
  }

}
