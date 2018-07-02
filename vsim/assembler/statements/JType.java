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
 * The class JType represents an j-type RISC-V statement.
 */
public final class JType extends Statement {

  /** j-type min immediate value {@value} */
  private static final int MIN_VAL = -1048576;
  /** j-type max immediate value {@value} */
  private static final int MAX_VAL = 1048575;

  /** register destiny */
  private String rd;
  /** jump label */
  private String label;
  /** target address */
  private Relocation target;

  /**
   * Unique constructor that initializes a newly JType statement.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rd register destiny
   * @param target target address
   */
  public JType(String mnemonic, DebugInfo debug, String rd, String target) {
    super(mnemonic, debug);
    this.rd = rd;
    this.label = target;
    this.target = new Relocation(Relocation.DEFAULT, target, debug);
  }

  @Override
  public void resolve() {
    this.target.resolve(0);
  }

  @Override
  public void build(int pc) {
    int imm = this.target.resolve(pc);
    if (Data.inRange(imm, JType.MIN_VAL, JType.MAX_VAL)) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int opcode = inst.getOpCode();
      int rd = Globals.regfile.getRegisterNumber(this.rd);
      this.code.set(InstructionField.IMM_20, imm >>> 20);
      this.code.set(InstructionField.IMM_10_1, imm >>> 1);
      this.code.set(InstructionField.IMM_19_12, imm >>> 12);
      this.code.set(InstructionField.IMM_11J, imm >>> 11);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.RD, rd);
    } else
      Errors.add(
        this.debug,
        "assembler",
        "jump to '" + this.label + "' too far"
      );
  }

}
