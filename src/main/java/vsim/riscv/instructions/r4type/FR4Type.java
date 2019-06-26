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

package vsim.riscv.instructions.r4type;

import vsim.State;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;


/** General form of a r4-type instruction. */
abstract class FR4Type extends Instruction {

  /**
   * Creates a new r4-type instruction.
   *
   * @param mnemonic the instruction mnemonic
   */
  protected FR4Type(String mnemonic) {
    super(Format.R4, mnemonic);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param rs3 register source 3
   * @return the computed result
   */
  protected abstract float compute(float rs1, float rs2, float rs3);

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    float rs3 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS3));
    state.fregfile().setRegister(code.get(InstructionField.RD), compute(rs1, rs2, rs3));
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
    int rs3 = code.get(InstructionField.RS3);
    return String.format("%s f%d, f%d, f%d, f%d", op, rd, rs1, rs2, rs3);
  }

}
