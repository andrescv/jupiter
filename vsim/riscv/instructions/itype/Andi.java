package vsim.riscv.instructions.itype;


public final class Andi extends IType {

  public Andi() {
    super(
      "andi",
      "andi rd, rs1, imm",
      "set rd = rs1 & imm, bitwise and"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 & imm;
  }

}
