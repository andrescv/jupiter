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

import vsim.Globals;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/**
 * The Fclass class represents a {@code fclass.s} instruction.
 */
public final class Fclasss extends Instruction {

  /**
   * Unique constructor that initializes a newly Fclasss instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fclasss() {
    super(Format.R, "fclass.s", "fclass.s rd, rs1",
        "set x[rd] = 10-bit mask that indicates the class of the floating-point register f[rs1]");
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct7() {
    return 0b1110000;
  }

  @Override
  public void execute(MachineCode code) {
    int out = 0;
    float f = Globals.fregfile.getRegisterFloat(code.get(InstructionField.RS1));
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
    Globals.regfile.setRegister(code.get(InstructionField.RD), out);
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return String.format("%s x%d, f%d", op, rd, rs1);
  }

}
