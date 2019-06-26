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


/** RISC-V jalr (Jump And Link Register) instruction. */
public final class Jalr extends IType {

  /** Creates a new jalr instruction. */
  public Jalr() {
    super("jalr");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1100111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(State state, int rs1, int imm) {
    int pc = state.xregfile().getProgramCounter();
    // set the least-significant bit of the result to zero
    state.xregfile().setProgramCounter((rs1 + imm) & ~1);
    return pc + 4;
  }

}
