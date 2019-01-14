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


/**
 * The Fmvxw class represents a {@code fmv.x.w} instruction.
 */
public final class Fmvxw extends Instruction {

  /**
   * Unique constructor that initializes a newly Fmvxw instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fmvxw() {
    super(Format.R, "fmv.x.w", "fmv.x.w rd, rs1", "set x[rd] = f[rs1][31:0]");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct7() {
    return 0b1110000;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(code.get(InstructionField.RD),
        Globals.fregfile.getRegister(code.get(InstructionField.RS1)));
    Globals.regfile.incProgramCounter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return String.format("%s x%d, f%d", op, rd, rs1);
  }

}
