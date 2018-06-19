package vsim.riscv.instructions.itype;


public final class Srai extends IType {

  public Srai() {
    super(
      "srai",
      "srai rd, rs1, shamt",
      "set rd = rs1 >> shamt, arithmetic shift right"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
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
  protected int compute(int rs1, int imm) {
    return rs1 >> (imm & 0x1f);
  }

}
