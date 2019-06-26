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

package vsim.riscv.instructions.stype;

import vsim.State;
import vsim.exceptions.SimulationException;


/** RISC-V sh (Store Half) instruction. */
public final class Sh extends SType {

  /** Creates a new sh instruction. */
  public Sh() {
    super("sh");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b001;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setMemory(State state, int rs1, int rs2, int imm) throws SimulationException {
    state.memory().storeHalf(rs1 + imm, rs2);
  }

}
