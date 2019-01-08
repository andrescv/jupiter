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

package vsim.riscv.instructions;

/**
 * The class InstructionField describes how to get fields from instruction formats
 */
public final class InstructionField {

  /** the entire instruction bits */
  public static final InstructionField ALL = new InstructionField(0, 31);

  /** opcode field */
  public static final InstructionField OPCODE = new InstructionField(0, 6);

  /** funct3 field */
  public static final InstructionField FUNCT3 = new InstructionField(12, 14);

  /** funct5 field */
  public static final InstructionField FUNCT5 = new InstructionField(27, 31);

  /** funct7 field */
  public static final InstructionField FUNCT7 = new InstructionField(25, 31);

  /** rd register field */
  public static final InstructionField RD = new InstructionField(7, 11);

  /** rs1 register field */
  public static final InstructionField RS1 = new InstructionField(15, 19);

  /** rs2 register field */
  public static final InstructionField RS2 = new InstructionField(20, 24);

  /** rs3 register field */
  public static final InstructionField RS3 = new InstructionField(27, 31);

  /** shift ammount field */
  public static final InstructionField SHAMT = new InstructionField(20, 24);

  /** i-type imm field */
  public static final InstructionField IMM_11_0 = new InstructionField(20, 31);

  /** s-type imm[4:0] field */
  public static final InstructionField IMM_4_0 = new InstructionField(7, 11);

  /** s-type imm[11:5] field */
  public static final InstructionField IMM_11_5 = new InstructionField(25, 31);

  /** b-type imm[11] field */
  public static final InstructionField IMM_11B = new InstructionField(7);

  /** b-type imm[4:1] field */
  public static final InstructionField IMM_4_1 = new InstructionField(8, 11);

  /** b-type imm[10:5] field */
  public static final InstructionField IMM_10_5 = new InstructionField(25, 30);

  /** b-type imm[12] field */
  public static final InstructionField IMM_12 = new InstructionField(31);

  /** u-type imm[31:12] field */
  public static final InstructionField IMM_31_12 = new InstructionField(12, 31);

  /** j-type imm[19:12] field */
  public static final InstructionField IMM_19_12 = new InstructionField(12, 19);

  /** j-type imm[11] field */
  public static final InstructionField IMM_11J = new InstructionField(20);

  /** j-type imm[10:1] field */
  public static final InstructionField IMM_10_1 = new InstructionField(21, 30);

  /** j-type imm[20] field */
  public static final InstructionField IMM_20 = new InstructionField(31);

  /** RVF rounding mode field */
  public static final InstructionField RM = new InstructionField(12, 14);

  /** RVF fmt field */
  public static final InstructionField FMT = new InstructionField(25, 26);

  /** low bit */
  protected int lo;
  /** high bit */
  protected int hi;
  /** field mask */
  protected int mask;

  /**
   * Constructor that initializes a newly InstructionField object.
   *
   * @param lo low and unique bit this field represents
   */
  private InstructionField(int lo) {
    this(lo, lo);
  }

  /**
   * Constructor that initializes a newly InstructionField object.
   *
   * @param lo low bit this field represents
   * @param hi high bit this field represents
   */
  private InstructionField(int lo, int hi) {
    this.lo = lo;
    this.hi = hi;
    this.mask = 0;
    for (int i = lo, j = 0; i <= hi; i++, j++)
      this.mask |= 1 << j;
  }

}
