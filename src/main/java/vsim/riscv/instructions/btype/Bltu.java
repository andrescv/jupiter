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

package vsim.riscv.instructions.btype;


/** RISC-V bltu (Branch if Less Than, Unsigned) instruction. */
public final class Bltu extends BType {

  /** Creates a new bltu instruction */
  public Bltu() {
    super("bltu");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b110;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean comparison(int rs1, int rs2) {
    return Integer.compareUnsigned(rs1, rs2) < 0;
  }

}
