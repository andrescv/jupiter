package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Sltu extends RType {

  public Sltu() {
    super(
      "sltu",
      "sltu rd, rs1, rs2",
      "set rd = 1 if rs1 < rs2 else 0, unsigned comparison"
    );
  }

  @Override
  public int getFunct3() {
    return 0b011;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return ALU.ltu(rs1, rs2) ? 1 : 0;
  }

}
