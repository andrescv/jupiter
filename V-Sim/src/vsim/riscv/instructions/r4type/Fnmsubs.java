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

package vsim.riscv.instructions.r4type;

/**
 * The Fnmsubs class represents a {@code fnmsub.s} instruction.
 */
public final class Fnmsubs extends FR4Type {

  /**
   * Unique constructor that initializes a newly Fnmsubs instruction.
   *
   * @see vsim.riscv.instructions.r4type.FR4Type
   */
  public Fnmsubs() {
    super("fnmsub.s", "fnmsub.s rd, rs1, rs2, rs3", "set f[rd] = -f[rs1] * f[rs2] + f[rs3]");
  }

  @Override
  public int getOpCode() {
    return 0b1001011;
  }

  @Override
  public float compute(float rs1, float rs2, float rs3) {
    return -((rs1 * rs2) - rs3);
  }

}
