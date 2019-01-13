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


/**
 * The Lb class represents a lb instruction.
 */
public final class Lb extends IType {

  /**
   * Unique constructor that initializes a newly Lb instruction.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Lb() {
    super("lb", "lb rd, offset(rs1)", "set x[rd] = sext(memory[x[rs1] + sext(offset)][7:0])");
  }

  @Override
  public int getOpCode() {
    return 0b0000011;
  }

  @Override
  protected int compute(int rs1, int imm) throws SimulationException {
    return Globals.memory.loadByte(rs1 + imm);
  }

}
