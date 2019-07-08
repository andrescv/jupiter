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
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** RISC-V slli (Shift Left Logical Immediate) instruction. */
public final class Slli extends IType {

  /** Creates a new slli instruction. */
  public Slli() {
    super("slli");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b001;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(State state, int rs1, int imm) {
    return rs1 << (imm & 0x1f);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.imm(code, Format.I) & 0x1f;
    return String.format("%s x%d, x%d, %d", op, rd, rs1, imm);
  }

}
