package vsim.riscv.instructions.rtype;


public final class Srl extends RType {

  public Srl() {
    super(
      "srl",
      "srl rd, rs1, rs2",
      "set rd = rs1 >>> rs2[4:0], logical shift right"
    );
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 >>> (rs2 & 0x1f);
  }

}
