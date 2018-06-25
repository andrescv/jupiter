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

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


/**
 * The class RType represents a r-type RISC-V statement.
 */
public final class RType extends Statement {

  /** register destiny */
  private String rd;
  /** register source 1 */
  private String rs1;
  /** register source 2 */
  private String rs2;

  /**
   * Unique constructor that initializes a newly RType object.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   * @param rd register destiny
   * @param rs1 register source 1
   * @param rs2 register source 2
   */
  public RType(String mnemonic, DebugInfo debug,
               String rd, String rs1, String rs2) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  @Override
  public void resolve() {
    /* DO NOTHING */
  }

  @Override
  public void build(int pc) {
    Instruction inst = Globals.iset.get(this.mnemonic);
    int rd  = Globals.regfile.getRegisterNumber(this.rd);
    int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
    int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
    int opcode = inst.getOpCode();
    int funct3 = inst.getFunct3();
    int funct7 = inst.getFunct7();
    this.code.set(InstructionField.RD,  rd);
    this.code.set(InstructionField.RS1, rs1);
    this.code.set(InstructionField.RS2, rs2);
    this.code.set(InstructionField.OPCODE, opcode);
    this.code.set(InstructionField.FUNCT3, funct3);
    this.code.set(InstructionField.FUNCT7, funct7);
  }

}
