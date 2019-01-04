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
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.utils.Data;


/**
 * The class Shift represents a i-type RISC-V shift statement.
 */
public final class Shift extends Statement {

  /** i-type shift min immediate value {@value} */
  private static final int MIN_VAL = 0;
  /** i-type shift max immediate value {@value} */
  private static final int MAX_VAL = 31;

  /** register destiny */
  private String rd;
  /** register source 1 */
  private String rs1;
  /** shift amount */
  private int shamt;

  /**
   * Unique constructor that initializes a newly Shift statement.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rd register destiny
   * @param rs1 register source 1
   * @param shamt shift amount
   */
  public Shift(String mnemonic, DebugInfo debug, String rd, String rs1, int shamt) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.shamt = shamt;
  }

  @Override
  public void resolve() {
    /* DO NOTHING */
  }

  @Override
  public void build(int pc) {
    // check range
    if (Data.inRange(this.shamt, Shift.MIN_VAL, Shift.MAX_VAL)) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd = Globals.regfile.getRegisterNumber(this.rd);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      int funct7 = inst.getFunct7();
      this.code.set(InstructionField.RD, rd);
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.SHAMT, this.shamt);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
      this.code.set(InstructionField.FUNCT7, funct7);
    } else
      Errors.add(this.debug, "assembler", "shift amount '" + this.shamt + "' out of range should be between 0 and 31");
  }

}
