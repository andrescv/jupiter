package vsim.riscv.instructions.rtype;


public final class Or extends RType {

  public Or() {
    super(
      "or",
      "or rd, rs1, rs2",
      "set rd = rs1 | rs2, bitwise or"
    );
  }

  @Override
  public int getFunct3() {
    return 0b110;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 | rs2;
  }

}
