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


/** RISC-V jalr (Jump And Link Register) instruction. */
public final class JALR extends Instruction {

  /** Creates a new jalr instruction. */
  public JALR() {
    super(Format.I, "jalr", 0b1100111, 0b000, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int rs1 = state.xregfile().getRegister(code.get(InstructionField.RS1));
    int pc = state.xregfile().getProgramCounter();
    int imm = Data.imm(code, Format.I);
    state.xregfile().setRegister(code.get(InstructionField.RD), pc + 4);
    state.xregfile().setProgramCounter((rs1 + imm) & ~1);
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.imm(code, Format.I);
    return String.format("jalr x%d, x%d, %d", rd, rs1, imm);
  }

}
