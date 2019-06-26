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

package vsim.riscv.instructions.btype;

import vsim.State;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General form of a b-type instruction. */
abstract class BType extends Instruction {

  /**
   * Creates a new b-type instruction.
   *
   * @param mnemonic the instruction mnemonic
   */
  protected BType(String mnemonic) {
    super(Format.B, mnemonic);
  }

  /**
   * Compares the two registers of the instruction.
   *
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @return the comparison result
   */
  protected abstract boolean comparison(int rs1, int rs2);

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1100011;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code, State state) {
    int rs1 = state.xregfile().getRegister(code.get(InstructionField.RS1));
    int rs2 = state.xregfile().getRegister(code.get(InstructionField.RS2));
    if (comparison(rs1, rs2)) {
      int pc = state.xregfile().getProgramCounter();
      state.xregfile().setProgramCounter(pc + Data.imm(code, Format.B));
    } else {
      state.xregfile().incProgramCounter();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int imm = Data.imm(code, Format.B);
    return String.format("%s x%d, x%d, %d", op, rs1, rs2, imm);
  }

}
