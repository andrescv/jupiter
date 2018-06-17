package vsim.riscv.instructions.itype;


public final class Srai extends IType {

  public Srai() {
    super(
      "srai",
      "srai rd, rs1, shamt",
      "set rd = rs1 >> shamt, arithmetic shift right"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b101;
    this.funct7 = 0b0100000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 >> (imm & 0x1f);
  }

}
