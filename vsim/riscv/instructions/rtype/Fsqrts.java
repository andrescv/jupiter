package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Fsqrts extends FRType {

  public Fsqrts() {
    super(
      "fsqrt.s",
      "fsqrt.s frd, frs1",
      "set frd = sqrt(frs1)"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct5() {
    return 0b01011;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return ALU.sqrt(rs1);
  }

}
