package vsim.riscv.instructions.rtype;


public final class Srl extends RType {

  public Srl() {
    super(
      "srl",
      "srl rd, rs1, rs2",
      "set rd = rs1 >> rs2[0:4], logical shift right"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b101;
    this.funct7 = 0b0000000;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 >>> (rs2 & 0x1f);
  }

}
