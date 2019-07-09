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

import vsim.State;
import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** RISC-V fsgnj.s (Floating-point Sign Inject, Single-Precision) instruction. */
public final class FSGNJS extends Instruction {

  /** Creates a new fsgnj.s instruction. */
  public FSGNJS() {
    super(Format.R, "fsgnj.s", 0b1010011, 0b000, 0b0010000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    int ax = Float.floatToIntBits(rs1) & (Data.EXPONENT_MASK | Data.FRACTION_MASK);
    int bx = Float.floatToIntBits(rs2) & Data.SIGN_MASK;
    state.fregfile().setRegister(code.get(InstructionField.RD), Float.intBitsToFloat(ax | bx));
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return String.format("fsgnj.s f%d, f%d, f%d", rd, rs1, rs2);
  }

}
