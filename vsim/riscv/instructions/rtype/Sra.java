package vsim.riscv.instructions.rtype;


public final class Sra extends RType {

  public Sra() {
    super(
      "sra",
      "sra rd, rs1, rs2",
      "set rd = rs1 >> rs2[4:0], arithmetic shift right"
    );
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  public int getFunct7() {
    return 0b0100000;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 >> (rs2 & 0x1f);
  }

}
