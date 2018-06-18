package vsim.riscv.instructions.rtype;


public final class Xor extends RType {

  public Xor() {
    super(
      "xor",
      "xor rd, rs1, rs2",
      "set rd = rs1 ^ rs2, bitwise xor"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b100;
    this.funct7 = 0b0000000;
}

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 ^ rs2;
  }

}
