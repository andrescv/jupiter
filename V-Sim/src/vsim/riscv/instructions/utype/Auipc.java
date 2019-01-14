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

import vsim.Globals;


/**
 * The Auipc class represents a auipc instruction.
 */
public final class Auipc extends UType {

  /**
   * Unique constructor that initializes a newly Auipc instruction.
   *
   * @see vsim.riscv.instructions.utype.UType
   */
  public Auipc() {
    super("auipc", "auipc rd, imm", "set x[rd] = imm << 12 + pc");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b0010111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int compute(int imm) {
    return (imm << 12) + Globals.regfile.getProgramCounter();
  }

}
