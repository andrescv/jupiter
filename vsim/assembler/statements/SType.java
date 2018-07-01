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

package vsim.assembler.statements;

import vsim.Errors;
import vsim.Globals;
import vsim.linker.Relocation;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


/**
 * The class SType represents a s-type RISC-V statement.
 */
public final class SType extends Statement {

  // min and max values of a s-type statement
  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  /** register source 1 */
  private String rs1;
  /** register source 2 */
  private String rs2;
  /** immediate value or relocation */
  private Object imm;

  /**
   * Unique constructor that initializes a newly SType object.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param imm immediate value or relocation
   */
  public SType(String mnemonic, DebugInfo debug,
               String rs1, String rs2, Object imm) {
    super(mnemonic, debug);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.imm = imm;
  }

  @Override
  public void resolve() {
    String filename = this.getDebugInfo().getFilename();
    if (this.imm instanceof Relocation)
      ((Relocation) this.imm).resolve(0, filename);
  }

  @Override
  public void build(int pc) {
    String filename = this.getDebugInfo().getFilename();
    int imm;
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(pc, filename);
    else
      imm = (int) this.imm;
    if (!((imm > SType.MAX_VAL) || (imm < SType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.RS2, rs2);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
      this.code.set(InstructionField.IMM_4_0, imm);
      this.code.set(InstructionField.IMM_11_5, imm >>> 5);
    } else
      Errors.add(
        this.getDebugInfo(),
        "assembler",
        "immediate '" + imm + "' out of range should be between -2048 and 2047"
      );
  }

}
