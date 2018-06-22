package vsim.riscv.instructions.rtype;


public final class Div extends RType {

  public Div() {
    super(
      "div",
      "div rd, rs1, rs2",
      "set rd = rs1 / rs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b100;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    if (rs2 != 0)
      return rs1 / rs2;
    return 0xffffffff;
  }

}
