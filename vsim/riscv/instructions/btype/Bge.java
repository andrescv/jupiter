package vsim.riscv.instructions.btype;


public final class Bge extends BType {

  public Bge() {
    super(
      "bge",
      "bge rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 >= rs2, signed comparison"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1100011;
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 >= rs2;
  }

}
