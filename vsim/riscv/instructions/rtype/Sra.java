package vsim.riscv.instructions.rtype;


public final class Sra extends RType {

  public Sra() {
    super(
      "sra",
      "sra rd, rs1, rs2",
      "set rd = rs1 >> rs2[4:0], arithmetic shift right"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b101;
    this.funct7 = 0b0100000;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 >> (rs2 & 0x1f);
  }

}
