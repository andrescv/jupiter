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
 * The Flw class represents a flw instruction.
 */
public final class Flw extends Instruction {

  /**
   * Unique constructor that initializes a newly Flw instruction.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  public Flw() {
    super(Format.I, "flw", "flw rd, offset(rs1)", "set f[rd] = memory[x[rs1] + sext(offset)][31:0]");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0000111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b010;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void execute(MachineCode code) throws SimulationException {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int imm = code.get(InstructionField.IMM_11_0);
    Globals.fregfile.setRegister(code.get(InstructionField.RD),
        Globals.memory.loadWord(rs1 + Data.signExtend(imm, 12)));
    Globals.regfile.incProgramCounter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.signExtend(code.get(InstructionField.IMM_11_0), 12);
    return String.format("%s f%d, x%d, %d", op, rd, rs1, imm);
  }

}
