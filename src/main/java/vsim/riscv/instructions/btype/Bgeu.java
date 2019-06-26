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


/** RISC-V bgeu (Branch if Greater Than or Equal, Unsigned) instruction. */
public final class Bgeu extends BType {

  /** Creates a new bgeu instruction */
  public Bgeu() {
    super("bgeu");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getFunct3() {
    return 0b111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected boolean comparison(int rs1, int rs2) {
    int cmp = Integer.compareUnsigned(rs1, rs2);
    return (cmp == 0) || (cmp > 0);
  }

}
