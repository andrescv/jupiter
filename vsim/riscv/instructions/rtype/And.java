package vsim.riscv.instructions.rtype;


public final class And extends RType {

  public And() {
    super(
      "and",
      "and rd, rs1, rs2",
      "set rd = rs1 & rs2, bitwise and"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 & rs2;
  }

}
