package vsim.riscv.instructions.rtype;


public final class Sub extends RType {

  public Sub() {
    super(
      "sub",
      "sub rd, rs1, rs2",
      "set rd = rs1 - rs2, overflow is ignored"
    );
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 - rs2;
  }

}
