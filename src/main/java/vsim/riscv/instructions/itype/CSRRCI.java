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

import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.sim.State;
import vsim.utils.Data;


/** RISC-V csrrci (Control and Status Register Read and Clear Immediate) instruction. */
public final class CSRRCI extends Instruction {

  /** Creates a new csrrci instruction. */
  public CSRRCI() {
    super(Format.I, "csrrci", 0b1110011, 0b111, 0b0000000);
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
    return String.format("csrrci x%d, %d, %d", rd, csr, uimm);
  }

}
