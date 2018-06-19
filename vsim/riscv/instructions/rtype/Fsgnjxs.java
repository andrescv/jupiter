package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


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
    return ALU.fsgnjx(rs1, rs2);
  }

}
