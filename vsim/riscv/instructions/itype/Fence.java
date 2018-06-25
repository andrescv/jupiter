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

package vsim.riscv.instructions.itype;


/**
 * The Fence class represents a fence instruction.
 */
public final class Fence extends IType {

  /**
   * Unique constructor that initializes a newly Fence object.
   *
   * @see vsim.riscv.instructions.itype.IType
   */
  public Fence() {
    super(
      "fence",
      "fence",
      "used to order device I/O and memory accesses as viewed by other RISC-V harts, external devices or coprocessors"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0001111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    /* DO NOTHING FOR THE MOMENT */
    return 0;
  }

}
