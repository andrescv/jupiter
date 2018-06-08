package vsim.riscv.instructions.btype;


public final class Blt extends BType {

  public Blt() {
    super(
      "blt",
      "blt rs1, rs2, imm",
      "set pc = pc + imm if rs1 < rs2, signed comparison"
    );
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 < rs2;
  }

}
