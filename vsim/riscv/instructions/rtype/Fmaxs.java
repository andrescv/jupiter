package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Fmaxs extends FRType {

  public Fmaxs() {
    super(
      "fmax.s",
      "fmax.s frd, frs1, frs2",
      "set frd = max(frs1, frs2)"
    );
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct5() {
    return 0b00101;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return ALU.fmax(rs1, rs2);
  }

}
