package vsim.riscv.instructions.itype;


public final class Xori extends IType {

  public Xori() {
    super(
      "xori",
      "xori rd, rs1, imm",
      "set rd = rs1 ^ sext(imm), bitwise xor"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b100;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 ^ imm;
  }

}
