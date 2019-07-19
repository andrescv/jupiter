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

package jvpiter.asm.stmts;

import java.io.File;

import jvpiter.Globals;
import jvpiter.asm.Relocation;
import jvpiter.exc.AssemblerException;
import jvpiter.exc.RelocationException;
import jvpiter.riscv.instructions.Instruction;
import jvpiter.riscv.instructions.InstructionField;
import jvpiter.riscv.instructions.MachineCode;
import jvpiter.utils.Data;


/** General representation of a b-type statement .*/
public final class BType extends Statement {

  /** register source 1 */
  private int rs1;
  /** register source 2 */
  private int rs2;
  /** branch offset */
  private Object offset;

  /**
   * Creates a new b-type statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param offset target offset (label or immediate)
   */
  public BType(File file, int line, String mnemonic, int rs1, int rs2, Object offset) {
    super(file, line, mnemonic);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.offset = offset;
  }

  /**
   * Creates a new b-type statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public BType(String mnemonic, MachineCode code) {
    super(mnemonic, code);
  }

  /** {@inheritDoc} */
  @Override
  public void check()  throws AssemblerException {
    getIMM(0);
  }

  /** {@inheritDoc} */
  @Override
  public void build(int pc) throws AssemblerException {
    int imm = getIMM(pc);
    if (Data.inRange(imm, -4096, 4095)) {
      Instruction inst = Globals.iset.get(mnemonic);
      code.set(InstructionField.RS1, rs1);
      code.set(InstructionField.RS2, rs2);
      code.set(InstructionField.IMM_11B, imm >>> 11);
      code.set(InstructionField.IMM_4_1, imm >>> 1);
      code.set(InstructionField.IMM_12, imm >>> 12);
      code.set(InstructionField.IMM_10_5, imm >>> 5);
      code.set(InstructionField.OPCODE, inst.getOpCode());
      code.set(InstructionField.FUNCT3, inst.getFunct3());
    } else {
      throw new AssemblerException("branch to " + imm + " too far, should be between -4096 and 4095");
    }
  }

  /**
   * Returns instruction immediate.
   *
   * @param pc program counter
   * @return instruction immediate
   * @throws RelocationException if the relocation (if any) could not be resolved
   */
  private int getIMM(int pc) throws RelocationException {
    if (offset instanceof Relocation) {
      return ((Relocation) offset).resolve(pc);
    }
    return (int) offset;
  }

}
