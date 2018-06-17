package vsim.riscv.instructions.itype;


public final class Slti extends IType {

  public Slti() {
    super(
      "slti",
      "slti rd, rs1, imm",
      "set rd = 1 if rs1 < sext(imm) else 0, signed comparison"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b010;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return (rs1 < imm) ? 1 : 0;
  }

}
