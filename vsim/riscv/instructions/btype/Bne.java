package vsim.riscv.instructions.btype;


public final class Bne extends BType {

  public Bne() {
    super(
      "bne",
      "bne rs1, rs2, imm",
      "set pc = pc + imm if rs1 != rs2"
    );
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 != rs2;
  }

}
