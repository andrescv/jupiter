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

package jupiter.riscv.instructions.utype;

import jupiter.exc.SimulationException;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.Instruction;
import jupiter.riscv.instructions.InstructionField;
import jupiter.riscv.instructions.MachineCode;
import jupiter.sim.State;
import jupiter.utils.Data;


/** RISC-V auipc () instruction. */
public final class AUIPC extends Instruction {

  /** Creates a new auipc instruction. */
  public AUIPC() {
    super(Format.U, "auipc", 0b0010111, 0b000, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int pc = state.xregfile().getProgramCounter();
    state.xregfile().setRegister(code.get(InstructionField.RD), (Data.imm(code, Format.U) << 12) + pc);
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int imm = Data.imm(code, Format.U);
    return String.format("auipc x%d, %d", rd, imm);
  }

}
