package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Divu extends RType {

  public Divu() {
    super(
      "divu",
      "divu rd, rs1, rs2",
      "set rd = unsigned(rs1) / unsigned(rs2)"
    );
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.divu(rs1, rs2);
  }

}
