package vsim.riscv.instructions.rtype;


public final class And extends RType {

  public And() {
    super(
      "and",
      "and rd, rs1, rs2",
      "set rd = rs1 & rs2, bitwise and"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b111;
    this.funct7 = 0b0000000;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 & rs2;
  }

}
