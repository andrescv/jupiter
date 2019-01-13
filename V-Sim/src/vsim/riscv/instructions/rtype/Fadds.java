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
 * The Fadds class represents a {@code fadd.s} instruction.
 */
public final class Fadds extends FRType {

  /**
   * Unique constructor that initializes a newly Fadds instruction.
   *
   * @see vsim.riscv.instructions.rtype.FRType
   */
  public Fadds() {
    super("fadd.s", "fadd.s rd, rs1, rs2", "set f[rd] = f[rs1] + f[rs2]");
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return rs1 + rs2;
  }

}
