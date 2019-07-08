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
import vsim.exc.AssemblerException;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General representation of a i-type shift statement .*/
public final class Shift extends Statement {

  /** destination register */
  private int rd;
  /** register source 1 */
  private int rs1;
  /** shift amount */
  private int shamt;

  /**
   * Creates a new i-type shift statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param shamt shift amount
   */
  public Shift(Path file, int line, String mnemonic, int rd, int rs1, int shamt) {
    super(file, line, mnemonic);
    this.rd = rd;
    this.rs1 = rs1;
    this.shamt = shamt;
  }

  /**
   * Creates a new i-type shift statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public Shift(String mnemonic, MachineCode code) {
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
    if (Data.inRange(shamt, 0, 31)) {
      Instruction inst = Globals.iset.get(mnemonic);
      code.set(InstructionField.RD, rd);
      code.set(InstructionField.RS1, rs1);
      code.set(InstructionField.FUNCT7, inst.getFunct7());
      code.set(InstructionField.SHAMT, shamt);
      code.set(InstructionField.OPCODE, inst.getOpCode());
      code.set(InstructionField.FUNCT3, inst.getFunct3());
    } else {
      throw new AssemblerException("shift amount '" + shamt + "' out of range should be between 0 and 31");
    }
  }

}
