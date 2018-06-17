package vsim.riscv.instructions.itype;


public final class Andi extends IType {

  public Andi() {
    super(
      "andi",
      "andi rd, rs1, imm",
      "set rd = rs1 & sext(imm), bitwise and"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 & imm;
  }

}
