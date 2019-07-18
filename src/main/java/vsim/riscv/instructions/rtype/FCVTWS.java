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

package vsim.riscv.instructions.rtype;

import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.sim.State;


/** RISC-V fcvt.w.s (Floating-point Convert to Word from Single) instruction. */
public final class FCVTWS extends Instruction {

  /** Creates a new fcvt.w.s instruction. */
  public FCVTWS() {
    super(Format.R, "fcvt.w.s", 0b1010011, 0b111, 0b1100000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float value = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    int result;
    if (Float.isNaN(value))
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
    return String.format("fcvt.w.s x%d, f%d", rd, rs1);
  }

}
