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
import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** RISC-V flw (Floating-point Load Word) instruction. */
public final class Flw extends Instruction {

  /** Creates a new flw instruction. */
  public Flw() {
    super(Format.I, "flw");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0000111;
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
  public void execute(MachineCode code, State state) throws SimulationException {
    int rd = code.get(InstructionField.RD);
    int rs1 = state.xregfile().getRegister(code.get(InstructionField.RS1));
    state.fregfile().setRegister(rd, state.memory().loadWord(rs1 + Data.imm(code, Format.I)));
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
    int imm = Data.imm(code, Format.I);
    return String.format("%s f%d, x%d, %d", op, rd, rs1, imm);
  }

}
