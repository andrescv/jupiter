package vsim.riscv.instructions.itype;


public final class Andi extends IType {

  public Andi() {
    super(
      "andi",
      "andi rd, rs1, imm",
      "set rd = rs1 & sext(imm), bitwise and"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 & imm;
  }

}
