package vsim.riscv.instructions.rtype;


public final class Mul extends RType {

  public Mul() {
    super(
      "mul",
      "mul rd, rs1, rs2",
      "set rd = rs1 * rs2, overflow is ignored"
    );
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 * rs2;
  }

}
