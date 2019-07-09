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


/** RISC-V fclass.s (Floating-point Classify, Single-Precision) instruction. */
public final class FCLASSS extends Instruction {

  /** Creates a new fclass.s instruction. */
  public FCLASSS() {
    super(Format.R, "fclass.s", 0b1010011, 0b001, 0b1110000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    int out = 0;
    float f = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    int bits = Float.floatToRawIntBits(f);
    // set flags
    boolean infOrNaN = Float.isNaN(f) || Float.isInfinite(f);
    boolean subnormalOrZero = (bits & Data.EXPONENT_MASK) == 0;
    boolean sign = ((bits & Data.SIGN_MASK) >> 31) != 0;
    boolean fracZero = (bits & Data.FRACTION_MASK) == 0;
    boolean isNaN = Float.isNaN(f);
    boolean isSNaN = Data.signalingNaN(f);
    // build 10-bit mask
    if (sign && infOrNaN && fracZero)
      out |= 1 << 0;
    if (sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 1;
    if (sign && subnormalOrZero && !fracZero)
      out |= 1 << 2;
    if (sign && subnormalOrZero && fracZero)
      out |= 1 << 3;
    if (!sign && infOrNaN && fracZero)
      out |= 1 << 7;
    if (!sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 6;
    if (!sign && subnormalOrZero && !fracZero)
      out |= 1 << 5;
    if (!sign && subnormalOrZero && fracZero)
      out |= 1 << 4;
    if (isNaN && isSNaN)
      out |= 1 << 8;
    if (isNaN && !isSNaN)
      out |= 1 << 9;
    state.xregfile().setRegister(code.get(InstructionField.RD), out);
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return String.format("fclass.s x%d, f%d", rd, rs1);
  }

}
