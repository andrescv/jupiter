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

import vsim.State;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General form of a j-type instruction. */
abstract class JType extends Instruction {

  /**
   * Creates a new j-type instruction.
   *
   * @param mnemonic the instruction mnemonic
   */
  protected JType(String mnemonic) {
    super(Format.J, mnemonic);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param imm immediate value
   * @return the computed result
   */
  protected abstract int compute(State state, int imm);

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) {
    state.xregfile().setRegister(code.get(InstructionField.RD), compute(state, Data.imm(code, Format.J)));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rd = code.get(InstructionField.RD);
    int imm = Data.imm(code, Format.J);
    return String.format("%s x%d, %d", op, rd, imm);
  }

}
