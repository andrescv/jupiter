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

package jupiter.riscv.instructions.itype;

import jupiter.exc.SimulationException;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.Instruction;
import jupiter.riscv.instructions.InstructionField;
import jupiter.riscv.instructions.MachineCode;
import jupiter.sim.State;
import jupiter.utils.Data;


/** RISC-V csrrwi (Control and Status Register Read and Write Immediate) instruction. */
public final class CSRRWI extends Instruction {

  /** Creates a new csrrwi instruction. */
  public CSRRWI() {
    super(Format.I, "csrrwi", 0b1110011, 0b101, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    // TODO: implement this
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int uimm = code.get(InstructionField.RS1);
    int csr = Data.imm(code, Format.I) & 0xfff;
    return String.format("csrrwi x%d, %d, %d", rd, csr, uimm);
  }

}
