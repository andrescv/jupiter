/*
Copyright (C) 2018 Andres Castellanos

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

package vsim.riscv.instructions.btype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The class BType represents the general form of a b-type instruction.
 */
abstract class BType extends Instruction {

  /**
   * Unique constructor that initializes a newly BType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected BType(String mnemonic, String usage, String description) {
    super(Format.B, mnemonic, usage, description);
  }

  /**
   * This method compares the two registers of the instruction.
   *
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @return the comparison result
   */
  protected abstract boolean comparison(int rs1, int rs2);

  @Override
  public int getOpCode() {
    return 0b1100011;
  }

  /**
   * This method returns the immediate value of a b-type instruction.
   *
   * @param code instruction machine code
   * @return immediate value
   */
  private int getImm(MachineCode code) {
    int imm_4_1 = code.get(InstructionField.IMM_4_1);
    int imm_10_5 = code.get(InstructionField.IMM_10_5);
    int imm_11 = code.get(InstructionField.IMM_11B);
    int imm_12 = code.get(InstructionField.IMM_12);
    int imm = (imm_12 << 11 | imm_11 << 10 | imm_10_5 << 4 | imm_4_1) << 1;
    return Data.signExtend(imm, 13);
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    boolean cmp = this.comparison(rs1, rs2);
    if (cmp) {
      int pc = Globals.regfile.getProgramCounter();
      Globals.regfile.setProgramCounter(pc + this.getImm(code));
    } else
      Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int imm = this.getImm(code);
    return Colorize.cyan(String.format("%s x%d, x%d, %d", op, rs1, rs2, imm));
  }

}
