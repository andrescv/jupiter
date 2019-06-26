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

package vsim.riscv.instructions.jtype;

import vsim.State;


/** RISC-V jal (Jump and Link) instruction. */
public final class Jal extends JType {

  /** Creates a new jal instruction. */
  public Jal() {
    super("jal");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1101111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(State state, int imm) {
    int pc = state.xregfile().getProgramCounter();
    state.xregfile().setProgramCounter(pc + imm);
    return pc + 4;
  }

}
