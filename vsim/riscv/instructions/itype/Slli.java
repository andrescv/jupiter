package vsim.riscv.instructions.itype;


public final class Slli extends IType {

  public Slli() {
    super(
      "slli",
      "slli rd, rs1, shamt",
      "set rd = rs1 << shamt, logical shift left"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 << (imm & 0x1f);
  }

}
