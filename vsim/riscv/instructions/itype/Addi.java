package vsim.riscv.instructions.itype;


public final class Addi extends IType {

  public Addi() {
    super(
      "addi",
      "addi rd, rs1, imm",
      "set rd = rs1 + sext(imm), overflow is ignored"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0010011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 + imm;
  }

}
