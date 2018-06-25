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

package vsim.riscv.instructions.utype;

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The class UType represents the general form of a u-type instruction.
 */
abstract class UType extends Instruction {

  /**
   * Unique constructor that initializes a newly UType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected UType(String mnemonic, String usage, String description) {
    super(Instruction.Format.U, mnemonic, usage, description);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param imm immediate value
   * @return the computed result
   */
  protected abstract int compute(int imm);

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(code.get(InstructionField.IMM_31_12))
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int imm = code.get(InstructionField.IMM_31_12);
    return Colorize.cyan(String.format("%s x%d, %d", op, rd, imm));
  }

}
