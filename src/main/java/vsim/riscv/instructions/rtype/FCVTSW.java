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


/** RISC-V fcvt.s.w (Floating-point Convert to Single from Word) instruction. */
public final class FCVTSW extends Instruction {

  /** Creates a new fcvt.s.w instruction. */
  public FCVTSW() {
    super(Format.R, "fcvt.s.w", 0b1010011, 0b111, 0b1101000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int value = state.xregfile().getRegister(code.get(InstructionField.RS1));
    state.fregfile().setRegister(code.get(InstructionField.RD), ((Integer) value).floatValue());
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return String.format("fcvt.s.w f%d, x%d", rd, rs1);
  }

}
