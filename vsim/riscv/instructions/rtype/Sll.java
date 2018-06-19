package vsim.riscv.instructions.rtype;


public final class Sll extends RType {

  public Sll() {
    super(
      "sll",
      "sll rd, rs1, rs2",
      "set rd = rs1 << rs2[4:0], logical shift left"
    );
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 << (rs2 & 0x1f);
  }

}
