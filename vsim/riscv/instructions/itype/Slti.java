package vsim.riscv.instructions.itype;


public final class Slti extends IType {

  public Slti() {
    super(
      "slti",
      "slti rd, rs1, imm",
      "set rd = 1 if rs1 < imm else 0, signed comparison"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return (rs1 < imm) ? 1 : 0;
  }

}
