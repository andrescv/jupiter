package vsim.riscv.instructions.itype;


public final class Ori extends IType {

  public Ori() {
    super(
      "ori",
      "ori rd, rs1, imm",
      "set rd = rs1 | imm, bitwise or"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b110;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 | imm;
  }

}
