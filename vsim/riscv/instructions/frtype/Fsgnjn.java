package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsgnjn extends FRType {

  public Fsgnjn() {
    super(
      "fsgnjn.s",
      "fsgnjn.s frd, frs1, frs2",
      "set frd = {~frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fsgnjn(rs1, rs2);
  }

}
