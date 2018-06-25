package vsim.riscv.instructions.btype;


public final class Bltu extends BType {

  public Bltu() {
    super(
      "bltu",
      "bltu rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 < rs2, unsigned comparison"
    );
  }

  @Override
  public int getFunct3() {
    return 0b110;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return Integer.compareUnsigned(rs1, rs2) < 0;
  }

}
