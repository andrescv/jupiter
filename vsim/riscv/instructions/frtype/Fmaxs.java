package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fmaxs extends FRType {

  public Fmaxs() {
    super(
      "fmax.s",
      "fmax.s frd, frs1, frs2",
      "set frd = max(frs1, frs2)"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fmax(rs1, rs2);
  }

}
