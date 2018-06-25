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

package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The class FRType represents the general form of a floating point
 * r-type instruction.
 */
abstract class FRType extends Instruction {

  /**
   * Unique constructor that initializes a newly BType object.
   *
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected FRType(String mnemonic, String usage, String description) {
    super(Instruction.Format.R, mnemonic, usage, description);
  }

  /**
   * This method computes the result of the instruction.
   *
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @return the computed result
   */
  protected abstract float compute(float rs1, float rs2);

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public void execute(MachineCode code) {
    float rs1 = Globals.fregfile.getRegister(code.get(InstructionField.RS1));
    float rs2 = Globals.fregfile.getRegister(code.get(InstructionField.RS2));
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(rs1, rs2)
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return Colorize.cyan(String.format("%s f%d, f%d, f%d", op, rd, rs1, rs2));
  }

}
