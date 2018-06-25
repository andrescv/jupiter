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
 * The Fsqrts class represents a fsqrt instruction.
 */
public final class Fsqrts extends FRType {

  /**
   * Unique constructor that initializes a newly Fsqrts object.
   *
   * @see vsim.riscv.instructions.rtype.FRType
   */
  public Fsqrts() {
    super(
      "fsqrt.s",
      "fsqrt.s frd, frs1",
      "set frd = sqrt(frs1)"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct5() {
    return 0b01011;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return (float)Math.sqrt(rs1);
  }

}
