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

package jvpiter.riscv.instructions.rtype;

import jvpiter.exc.SimulationException;
import jvpiter.riscv.instructions.Format;
import jvpiter.riscv.instructions.Instruction;
import jvpiter.riscv.instructions.InstructionField;
import jvpiter.riscv.instructions.MachineCode;
import jvpiter.sim.State;


/** RISC-V feq.s (Floating-point Equal, Single-Precision) instruction. */
public final class FEQS extends Instruction {

  /** Creates a new feq.s instruction. */
  public FEQS() {
    super(Format.R, "feq.s", 0b1010011, 0b010, 0b1010000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    int result = (rs1 == rs2) ? 1 : 0;
    state.xregfile().setRegister(code.get(InstructionField.RD), result);
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return String.format("feq.s x%d, f%d, f%d", rd, rs1, rs2);
  }

}
