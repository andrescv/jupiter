package vsim.riscv.instructions.rtype;


public final class Rem extends RType {

  public Rem() {
    super(
      "rem",
      "rem rd, rs1, rs2",
      "set rd = rs1 % rs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b110;
  }

  @Override
  public int getFunct7() {
    return 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 % rs2;
  }

}
