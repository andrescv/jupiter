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

package vsim.riscv.instructions.rtype;


/**
 * The Remu class represents a remu instruction.
 */
public final class Remu extends RType {

  /**
   * Unique constructor that initializes a newly Remu instruction.
   *
   * @see vsim.riscv.instructions.rtype.RType
   */
  public Remu() {
    super(
      "remu",
      "remu rd, rs1, rs2",
      "set x[rd] = x[rs1] unsigned(%) x[rs2]"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    if (rs2 != 0)
      return Integer.remainderUnsigned(rs1, rs2);
    return rs1;
  }

}
