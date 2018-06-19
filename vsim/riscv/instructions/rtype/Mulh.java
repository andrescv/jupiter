package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Mulh extends RType {

  public Mulh() {
    super(
      "mulh",
      "mulh rd, rs1, rs2",
      "set rd = (rs1 * rs2) >> XLEN"
    );
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return ALU.mulh(rs1, rs2);
  }

}
