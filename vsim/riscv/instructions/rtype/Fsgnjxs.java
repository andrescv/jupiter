package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Fsgnjxs extends FRType {

  public Fsgnjxs() {
    super(
      "fsgnjx.s",
      "fsgnjx.s frd, frs1, frs2",
      "set frd = {frs1[31] ^ frs2[31], frs1[30:0]}"
    );
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  public int getFunct5() {
    return 0b00100;
  }

  @Override
  public float compute(float rs1, float rs2) {
    int ax = Float.floatToIntBits(rs1);
    int bx = Float.floatToIntBits(rs2);
    int sign = (ax ^ bx) & Data.SIGN_MASK;
    int cx = ax & (Data.EXPONENT_MASK | Data.FRACTION_MASK);
    return Float.intBitsToFloat(sign | cx);
  }

}
