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

package vsim.riscv.instructions.rtype;

/**
 * The Srl class represents a srl instruction.
 */
public final class Srl extends RType {

  /**
   * Unique constructor that initializes a newly Srl instruction.
   *
   * @see vsim.riscv.instructions.rtype.RType
   */
  public Srl() {
    super("srl", "srl rd, rs1, rs2", "set x[rd] = x[rs1] >>> x[rs2][4:0], logical shift right");
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
  protected int compute(int rs1, int rs2) {
    return rs1 >>> (rs2 & 0x1f);
  }

}
