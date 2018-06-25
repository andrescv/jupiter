package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Fsgnjns extends FRType {

  public Fsgnjns() {
    super(
      "fsgnjn.s",
      "fsgnjn.s frd, frs1, frs2",
      "set frd = {~frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct5() {
    return 0b00100;
  }

  @Override
  public float compute(float rs1, float rs2) {
    int ax = Float.floatToIntBits(rs1) & (Data.EXPONENT_MASK | Data.FRACTION_MASK);
    int bx = ~Float.floatToIntBits(rs2) & Data.SIGN_MASK;
    return Float.intBitsToFloat(ax | bx);
  }

}
