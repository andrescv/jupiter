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
import vsim.exc.BreakpointException;
import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;


/** RISC-V ebreak (Environment Breakpoint) instruction. */
public final class EBREAK extends Instruction {

  /** Creates a new ebreak instruction. */
  public EBREAK() {
    super(Format.I, "ebreak", 0b1110011, 0b000, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    throw new BreakpointException(state.xregfile().getProgramCounter());
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    return "ebreak";
  }

}