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


/** RISC-V fmsub.s (Floating-point Fused Multiply-Subtract, Single-Precision) instruction. */
public final class Fmsubs extends FR4Type {

  /** Creates a new fmsub.s instruction. */
  public Fmsubs() {
    super("fmsub.s");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int getOpCode() {
    return 0b1000111;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public float compute(float rs1, float rs2, float rs3) {
    return (rs1 * rs2) - rs3;
  }

}
