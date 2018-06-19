package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


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
    return ALU.fsgnjn(rs1, rs2);
  }

}
