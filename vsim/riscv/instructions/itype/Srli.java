package vsim.riscv.instructions.itype;


public final class Srli extends IType {

  public Srli() {
    super(
      "srli",
      "srli rd, rs1, imm",
      "set rd = rs1 >> imm[0:4], logical shift right"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b101;
    this.funct7 = 0b0000000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 >>> (imm & 0x1f);
  }

}
