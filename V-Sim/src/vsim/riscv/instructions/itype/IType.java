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

package vsim.riscv.instructions.itype;

import vsim.Globals;
import vsim.riscv.exceptions.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/**
 * The class IType represents the general form of a i-type instruction.
 */
abstract class IType extends Instruction {

  /**
   * Unique constructor that initializes a newly IType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected IType(String mnemonic, String usage, String description) {
    super(Format.I, mnemonic, usage, description);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param rs1 register source 1
   * @param imm immediate value
   * @return the computed result
   * @throws SimulationException if an exception occurs while calculating instruction result
   */
  protected abstract int compute(int rs1, int imm) throws SimulationException;

  @Override
  public void execute(MachineCode code) throws SimulationException {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int imm = Data.signExtend(code.get(InstructionField.IMM_11_0), 12);
    Globals.regfile.setRegister(code.get(InstructionField.RD), this.compute(rs1, imm));
    if (!this.getMnemonic().equals("jalr"))
      Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.signExtend(code.get(InstructionField.IMM_11_0), 12);
    return String.format("%s x%d, x%d, %d", op, rd, rs1, imm);
  }

}
