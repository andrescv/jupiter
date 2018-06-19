package vsim.riscv.instructions.btype;


public final class Blt extends BType {

  public Blt() {
    super(
      "blt",
      "blt rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 < rs2, signed comparison"
    );
  }

  @Override
  public int getFunct3() {
    return 0b100;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 < rs2;
  }

}
