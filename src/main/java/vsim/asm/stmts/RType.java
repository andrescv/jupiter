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

package vsim.asm.stmts;

import java.nio.file.Path;

import vsim.Globals;
import vsim.exceptions.AssemblerException;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;


/** General representation of a r-type statement .*/
public final class RType extends Statement {

  /** destination register */
  private int rd;
  /** register source 1 */
  private int rs1;
  /** register source 2 */
  private int rs2;

  /**
   * Creates a new r-type statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param rs2 register source 2
   */
  public RType(Path file, int line, String mnemonic, int rd, int rs1, int rs2) {
    super(file, line, mnemonic);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  /**
   * Creates a new r-type statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public RType(String mnemonic, MachineCode code) {
    super(mnemonic, code);
  }

  /** {@inheritDoc} */
  @Override
  public void check()  throws AssemblerException {
    // nothing here :]
  }

  /** {@inheritDoc} */
  @Override
  public void build(int pc) throws AssemblerException {
    Instruction inst = Globals.iset.get(mnemonic);
    code.set(InstructionField.RD, rd);
    code.set(InstructionField.RS1, rs1);
    code.set(InstructionField.RS2, rs2);
    code.set(InstructionField.OPCODE, inst.getOpCode());
    code.set(InstructionField.FUNCT3, inst.getFunct3());
    code.set(InstructionField.FUNCT7, inst.getFunct7());
  }

}
