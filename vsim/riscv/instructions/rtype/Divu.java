package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Divu extends RType {

  public Divu() {
    super(
      "divu",
      "divu rd, rs1, rs2",
      "set rd = rs1 unsigned(/) rs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    if (rs2 != 0)
      return ALU.divu(rs1, rs2);
    return 0xffffffff;
  }

}
