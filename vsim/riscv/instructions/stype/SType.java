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

package vsim.riscv.instructions.stype;

import vsim.Globals;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Colorize;
import vsim.utils.Data;


/**
 * The class SType represents the general form of a s-type instruction.
 */
abstract class SType extends Instruction {

  /**
   * Unique constructor that initializes a newly SType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected SType(String mnemonic, String usage, String description) {
    super(Format.S, mnemonic, usage, description);
  }

  @Override
  public int getOpCode() {
    return 0b0100011;
  }

  /**
   * This method sets the value of rs2 in memory[rs1 + imm]
   *
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param imm immediate offset
   */
  protected abstract void setMemory(int rs1, int rs2, int imm);

  private int getImm(MachineCode code) {
    int imm_11_5 = code.get(InstructionField.IMM_11_5);
    int imm_4_0 = code.get(InstructionField.IMM_4_0);
    int imm = (imm_11_5 << 5) | imm_4_0;
    return Data.signExtend(imm, 12);
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    this.setMemory(rs1, rs2, this.getImm(code));
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
