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
import vsim.exc.AssemblerException;
import vsim.exc.RelocationException;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** General representation of a i-type statement .*/
public final class IType extends Statement {

  /** destination register */
  private int rd;
  /** register source 1 */
  private int rs1;
  /** immediate */
  private Object imm;

  /**
   * Creates a new i-type statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param imm immediate
   */
  public IType(Path file, int line, String mnemonic, int rd, int rs1, Object imm) {
    super(file, line, mnemonic);
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

  /**
   * Creates a new i-type statement.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public IType(String mnemonic, MachineCode code) {
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
    if (Data.inRange(imm, -2048, 2047)) {
      Instruction inst = Globals.iset.get(mnemonic);
      code.set(InstructionField.RD, rd);
      code.set(InstructionField.RS1, rs1);
      code.set(InstructionField.IMM_11_0, imm);
      code.set(InstructionField.OPCODE, inst.getOpCode());
      code.set(InstructionField.FUNCT3, inst.getFunct3());
    } else {
      throw new AssemblerException("immediate '" + imm + "' out of range should be between -2048 and 2047");
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
    if (imm instanceof Relocation) {
      return ((Relocation) imm).resolve(pc);
    }
    return (int) imm;
  }

}
