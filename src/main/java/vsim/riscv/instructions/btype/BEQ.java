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

import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.sim.State;
import vsim.utils.Data;


/** RISC-V beq (Branch if Equal) instruction. */
public final class BEQ extends Instruction {

  /** Creates a new beq instruction. */
  public BEQ() {
    super(Format.B, "beq", 0b1100011, 0b000, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int rs1 = state.xregfile().getRegister(code.get(InstructionField.RS1));
    int rs2 = state.xregfile().getRegister(code.get(InstructionField.RS2));
    if (rs1 == rs2) {
      int pc = state.xregfile().getProgramCounter();
      state.xregfile().setProgramCounter(pc + Data.imm(code, Format.B));
    } else {
      state.xregfile().incProgramCounter();
    }
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int imm = Data.imm(code, Format.B);
    return String.format("beq x%d, x%d, %d", rs1, rs2, imm);
  }

}
