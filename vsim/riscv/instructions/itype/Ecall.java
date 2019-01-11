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

import static vsim.riscv.Ecall.handler;
import vsim.riscv.exceptions.SimulationException;


/**
 * The Ecall class represents an ecall instruction.
 */
public final class Ecall extends IType {

  /**
   * Unique constructor that initializes a newly Ecall instruction.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Ecall() {
    super("ecall", "ecall", "makes a request to the supporting execution environment");
  }

  @Override
  public int getOpCode() {
    return 0b1110011;
  }

  @Override
  protected int compute(int rs1, int imm) throws SimulationException {
    // call ecall handler
    handler();
    return 0;
  }

}
