package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


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
    return ALU.mulhu(rs1, rs2);
  }

}
