package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Remu extends RType {

  public Remu() {
    super(
      "remu",
      "remu rd, rs1, rs2",
      "set rd = rs1 unsigned(%) rs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    if (rs2 != 0)
      return ALU.remu(rs1, rs2);
    return rs1;
  }

}
