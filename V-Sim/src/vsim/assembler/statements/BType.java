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

package vsim.assembler.statements;

import vsim.Errors;
import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.linker.Relocation;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.utils.Data;


/**
 * The class BType represents a b-type RISC-V statement.
 */
public final class BType extends Statement {

  /** b-type min immediate value {@value} */
  private static final int MIN_VAL = -2048;
  /** b-type max immediate value {@value} */
  private static final int MAX_VAL = 2047;

  /** register source 1 */
  private String rs1;
  /** register source 2 */
  private String rs2;
  /** label of the branch */
  private String label;
  /** relocation expansion */
  private Relocation offset;

  /**
   * Unique constructor that initializes a newly BType statement.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param offset target offset (label)
   */
  public BType(String mnemonic, DebugInfo debug, String rs1, String rs2, String offset) {
    super(mnemonic, debug);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.label = offset;
    this.offset = new Relocation(Relocation.DEFAULT, offset, debug);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resolve() {
    this.offset.resolve(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(int pc) {
    int imm = this.offset.resolve(pc);
    if (Data.inRange(imm, BType.MIN_VAL, BType.MAX_VAL)) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.RS2, rs2);
      this.code.set(InstructionField.IMM_11B, imm >>> 11);
      this.code.set(InstructionField.IMM_4_1, imm >>> 1);
      this.code.set(InstructionField.IMM_12, imm >>> 12);
      this.code.set(InstructionField.IMM_10_5, imm >>> 5);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
    } else
      Errors.add(this.debug, "assembler", "branch to '" + this.label + "' too far");
  }

}
