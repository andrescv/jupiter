/*
Copyright (C) 2018 Andres Castellanos

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


/**
 * The Blt class represents a blt instruction.
 */
public final class Blt extends BType {

  /**
   * Unique constructor that initializes a newly Blt object.
   *
   * @see vsim.riscv.instructions.btype.BType
   */
  public Blt() {
    super(
      "blt",
      "blt rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 < rs2, signed comparison"
    );
  }

  @Override
  public int getFunct3() {
    return 0b100;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 < rs2;
  }

}
