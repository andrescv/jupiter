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


/** General representation of a r4-type statement .*/
public final class R4Type extends Statement {

  /** destination register */
  private int rd;
  /** register source 1 */
  private int rs1;
  /** register source 2 */
  private int rs2;
  /** register source 3 */
  private int rs3;

  /**
   * Creates a new r4-type statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param rs3 register source 3
   */
  public R4Type(Path file, int line, String mnemonic, int rd, int rs1, int rs2, int rs3) {
    super(file, line, mnemonic);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.rs3 = rs3;
  }

  /**
   * Creates a new r4-type statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public R4Type(String mnemonic, MachineCode code) {
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
    code.set(InstructionField.RS3, rs3);
    code.set(InstructionField.FMT, 0b00);
    code.set(InstructionField.RS2, rs2);
    code.set(InstructionField.RS1, rs1);
    code.set(InstructionField.RM, 0b111); // dynamic
    code.set(InstructionField.RD, rd);
    code.set(InstructionField.OPCODE, inst.getOpCode());
  }

}
