package vsim.riscv.instructions.rtype;


public final class Rem extends RType {

  public Rem() {
    super(
      "rem",
      "rem rd, rs1, rs2",
      "set rd = rs1 % rs2"
    );
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 % rs2;
  }

}
