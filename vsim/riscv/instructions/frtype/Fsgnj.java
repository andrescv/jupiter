package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsgnj extends FRType {

  public Fsgnj() {
    super(
      "fsgnj.s",
      "fsgnj.s frd, frs1, frs2",
      "set frd = {frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fsgnj(rs1, rs2);
  }

}
