package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulhu extends RType {

  public Mulhu() {
    super(
      "mulhu",
      "mulhu rd, rs1, rs2",
      "set rd = (unsigned(rs1) * unsigned(rs2)) >>> XLEN"
    );
  }

  @Override
  public int getFunct3() {
    return 0b011;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    long result = Integer.toUnsignedLong(rs1) * Integer.toUnsignedLong(rs2);
    return (int)(result >>> Data.WORD_LENGTH_BITS);
  }

}
