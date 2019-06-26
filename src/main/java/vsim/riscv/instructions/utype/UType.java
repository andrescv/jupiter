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

package vsim.riscv.instructions.utype;

import vsim.State;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General form of a u-type instruction. */
abstract class UType extends Instruction {

  /**
   * Creates a new u-type instruction.
   *
   * @param mnemonic the instruction mnemonic
   */
  protected UType(String mnemonic) {
    super(Format.U, mnemonic);
  }

  /**
   * Computes the result of the instruction.
   *
   * @param imm immediate value
   * @param state program state
   * @return the computed result
   */
  protected abstract int compute(State state, int imm);

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) {
    state.xregfile().setRegister(code.get(InstructionField.RD), compute(state, Data.imm(code, Format.U)));
    state.xregfile().incProgramCounter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rd = code.get(InstructionField.RD);
    int imm = Data.imm(code, Format.U);
    return String.format("%s x%d, %d", op, rd, imm);
  }

}
