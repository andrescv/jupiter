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
 * The Fsw class represents a fsw instruction.
 */
public final class Fsw extends Instruction {

  /**
   * Unique constructor that initializes a newly Fsw instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Fsw() {
    super(Format.S, "fsw", "fsw rs2, offset(rs1)", "set memory[x[rs1] + sext(offset)] = f[rd][31:0]");
  }

  @Override
  public int getOpCode() {
    return 0b0100111;
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  /**
   * This method gets the immediate value of an fsw instruction.
   *
   * @param code machine code
   * @return the immediate value
   */
  private int getImm(MachineCode code) {
    int imm_11_5 = code.get(InstructionField.IMM_11_5);
    int imm_4_0 = code.get(InstructionField.IMM_4_0);
    int imm = (imm_11_5 << 5) | imm_4_0;
    return Data.signExtend(imm, 12);
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.fregfile.getRegisterInt(code.get(InstructionField.RS2));
    Globals.memory.storeWord(rs1 + this.getImm(code), rs2);
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int imm = this.getImm(code);
    return Colorize.cyan(String.format("%s x%d, f%d, %d", op, rs1, rs2, imm));
  }

}
