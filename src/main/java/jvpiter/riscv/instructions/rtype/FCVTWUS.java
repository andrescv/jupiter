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


/** RISC-V fcvt.wu.s (Floating-point Convert to Unsigned Word from Single) instruction. */
public final class FCVTWUS extends Instruction {

  /** Creates a new fcvt.wu.s instruction. */
  public FCVTWUS() {
    super(Format.R, "fcvt.wu.s", 0b1010011, 0b111, 0b1100000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float value = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    int result;
    if (value < Integer.MIN_VALUE)
      result = 0;
    else if (value > Integer.MAX_VALUE || Float.isNaN(value))
      result = Integer.MAX_VALUE;
    else
      result = Math.round(value);
    state.xregfile().setRegister(code.get(InstructionField.RD), result);
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return String.format("fcvt.wu.s x%d, f%d", rd, rs1);
  }

}
