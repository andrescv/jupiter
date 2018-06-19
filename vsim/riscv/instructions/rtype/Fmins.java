package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Fmins extends FRType {

  public Fmins() {
    super(
      "fmin.s",
      "fmin.s frd, frs1, frs2",
      "set frd = min(frs1, frs2)"
    );
  }

  @Override
  public int getFunct5() {
    return 0b00101;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return ALU.fmin(rs1, rs2);
  }

}
