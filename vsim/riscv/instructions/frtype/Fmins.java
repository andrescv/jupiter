package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fmins extends FRType {

  public Fmins() {
    super(
      "fmin.s",
      "fmin.s frd, frs1, frs2",
      "set frd = min(frs1, frs2)"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fmin(rs1, rs2);
  }

}
