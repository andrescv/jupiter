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

import vsim.State;
import vsim.exc.SimulationException;


/** RISC-V lhu (Load Half, Unsigned) instruction. */
public final class Lhu extends IType {

  /** Creates a new lhu instruction. */
  public Lhu() {
    super("lhu");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0000011;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b101;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(State state, int rs1, int imm) throws SimulationException {
    return state.memory().loadHalfUnsigned(rs1 + imm);
  }

}
