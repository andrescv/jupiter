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

package vsim.riscv.instructions.jtype;

import vsim.Globals;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Colorize;
import vsim.utils.Data;


/**
 * The class JType represents the general form of a j-type instruction.
 */
abstract class JType extends Instruction {

  /**
   * Unique constructor that initializes a newly JType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected JType(String mnemonic, String usage, String description) {
    super(Format.J, mnemonic, usage, description);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param imm immediate value
   * @return the computed result
   */
  protected abstract int compute(int imm);

  /**
   * This method gets the immediate value of the instruction.
   *
   * @param code machine code
   * @return the immediate value
   */
  private int getImm(MachineCode code) {
    int imm_10_1 = code.get(InstructionField.IMM_10_1);
    int imm_11 = code.get(InstructionField.IMM_11J);
    int imm_19_12 = code.get(InstructionField.IMM_19_12);
    int imm_20 = code.get(InstructionField.IMM_20);
    int imm = (imm_20 << 19 | imm_19_12 << 11 | imm_11 << 10 | imm_10_1) << 1;
    return Data.signExtend(imm, 21);
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(code.get(InstructionField.RD), this.compute(this.getImm(code)));
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int imm = this.getImm(code);
    return Colorize.cyan(String.format("%s x%d, %d", op, rd, imm));
  }

}
