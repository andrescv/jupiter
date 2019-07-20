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

package jupiter.riscv.instructions.jtype;

import jupiter.exc.SimulationException;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.Instruction;
import jupiter.riscv.instructions.InstructionField;
import jupiter.riscv.instructions.MachineCode;
import jupiter.sim.State;
import jupiter.utils.Data;


/** RISC-V jal (Jump and Link) instruction. */
public final class JAL extends Instruction {

  /** Creates a new jal instruction. */
  public JAL() {
    super(Format.J, "jal", 0b1101111, 0b000, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int pc = state.xregfile().getProgramCounter();
    int imm = Data.imm(code, Format.J);
    state.xregfile().setRegister(code.get(InstructionField.RD), pc + 4);
    state.xregfile().setProgramCounter(pc + imm);
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int imm = Data.imm(code, Format.J);
    return String.format("jal x%d, %d", rd, imm);
  }

}
