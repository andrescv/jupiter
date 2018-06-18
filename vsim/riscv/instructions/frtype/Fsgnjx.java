package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsgnjx extends FRType {

  public Fsgnjx() {
    super(
      "fsgnjx.s",
      "fsgnjx.s frd, frs1, frs2",
      "set frd = {frs1[31] ^ frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fsgnjx(rs1, rs2);
  }

}
