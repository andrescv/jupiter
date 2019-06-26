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
import vsim.asm.Relocation;
import vsim.exceptions.AssemblerException;
import vsim.exceptions.RelocationException;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General representation of a j-type statement .*/
public final class JType extends Statement {

  /** destination register */
  private int rd;
  /** jump offset */
  private Object offset;

  /**
   * Creates a new j-type statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param offset jump offset
   */
  public JType(Path file, int line, String mnemonic, int rd, Object offset) {
    super(file, line, mnemonic);
    this.rd = rd;
    this.offset = offset;
  }

  /**
   * Creates a new j-type statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public JType(String mnemonic, MachineCode code) {
    super(mnemonic, code);
  }

  /** {@inheritDoc} */
  @Override
  public void check() throws AssemblerException {
    getIMM(0);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void build(int pc) throws AssemblerException {
    int imm = getIMM(pc);
    if (Data.inRange(imm, -1048576, 1048575)) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      code.set(InstructionField.IMM_20, imm >>> 20);
      code.set(InstructionField.IMM_10_1, imm >>> 1);
      code.set(InstructionField.IMM_19_12, imm >>> 12);
      code.set(InstructionField.IMM_11J, imm >>> 11);
      code.set(InstructionField.OPCODE, inst.getOpCode());
      code.set(InstructionField.RD, rd);
    } else {
      throw new AssemblerException("jump to " + imm + " too far, should be between -1048576 and 1048575");
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
