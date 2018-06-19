package vsim.riscv.instructions.itype;


public final class Ori extends IType {

  public Ori() {
    super(
      "ori",
      "ori rd, rs1, imm",
      "set rd = rs1 | sext(imm), bitwise or"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b110;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 | imm;
  }

}
