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
import vsim.utils.Data;
import vsim.linker.Relocation;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


/**
 * The class UType represents a u-type RISC-V statement.
 */
public final class UType extends Statement {

  /** u-type min immediate value {@value} */
  private static final int MIN_VAL = 0;
  /** u-type max immediate value {@value} */
  private static final int MAX_VAL = 1048575;

  /** register destiny */
  private String rd;
  /** immediate value or relocation */
  private Object imm;

  /**
   * Unique constructor that initializes a newly UType statement.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rd register destiny
   * @param imm immediate value or relocation
   */
  public UType(String mnemonic, DebugInfo debug, String rd, Object imm) {
    super(mnemonic, debug);
    this.rd = rd;
    this.imm = imm;
  }

  @Override
  public void resolve() {
    if (this.imm instanceof Relocation)
      ((Relocation) this.imm).resolve(0);
  }

  @Override
  public void build(int pc) {
    int imm;
    // get imm
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(pc);
    else
      imm = (int) this.imm;
    // check range
    if (Data.inRange(imm, UType.MIN_VAL, UType.MAX_VAL)) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd = Globals.regfile.getRegisterNumber(this.rd);
      int opcode = inst.getOpCode();
      this.code.set(InstructionField.RD, rd);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.IMM_31_12, imm);
    } else
      Errors.add(
        this.debug,
        "assembler",
        "immediate '" + imm + "' out of range should be between 0 and 1048575"
      );
  }

}
