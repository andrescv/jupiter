package vsim.riscv.instructions.btype;


public final class Beq extends BType {

  public Beq() {
    super(
      "beq",
      "beq rs1, rs2, imm",
      "set pc = pc + imm if rs1 == rs2"
    );
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 == rs2;
  }

}
