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

package vsim.riscv.instructions.utype;

import vsim.State;


/** RISC-V lui () instruction. */
public final class Lui extends UType {

  /** Creates a new lui instruction. */
  public Lui() {
    super("lui");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0110111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(State state, int imm) {
    return imm << 12;
  }

}
