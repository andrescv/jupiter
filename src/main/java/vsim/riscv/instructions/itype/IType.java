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

package vsim.riscv.instructions.itype;

import vsim.State;
import vsim.exceptions.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General form of a i-type instruction. */
abstract class IType extends Instruction {

  /**
   * Creates a new i-type instruction.
   *
   * @param mnemonic the instruction mnemonic
   */
  protected IType(String mnemonic) {
    super(Format.I, mnemonic);
  }

  /**
   * Computes the result of the instruction.
   *
   * @param rs1 register source 1
   * @param imm immediate value
   * @return the computed result
   * @throws VSimException if an exception occurs while calculating instruction result
   */
  protected abstract int compute(State state, int rs1, int imm) throws SimulationException;

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int rs1 = state.xregfile().getRegister(code.get(InstructionField.RS1));
    state.xregfile().setRegister(code.get(InstructionField.RD), compute(state, rs1, Data.imm(code, Format.I)));
    if (!getMnemonic().equals("jalr")) {
      state.xregfile().incProgramCounter();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.imm(code, Format.I);
    return String.format("%s x%d, x%d, %d", op, rd, rs1, imm);
  }

}
