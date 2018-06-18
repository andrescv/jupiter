package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsqrts extends FRType {

  public Fsqrts() {
    super(
      "fsqrt.s",
      "fsqrt.s frd, frs1",
      "set frd = sqrt(frs1)"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.sqrt(rs1);
  }

}
