package vsim.riscv.instructions.itype;


public final class Sltiu extends IType {

  public Sltiu() {
    super(
      "sltiu",
      "sltiu rd, rs1, imm",
      "set rd = 1 if rs1 < sext(imm) else 0, unsigned comparison"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  public int getFunct3() {
    return 0b011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0;
  }

}
