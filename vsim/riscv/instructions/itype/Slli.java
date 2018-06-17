package vsim.riscv.instructions.itype;


public final class Slli extends IType {

  public Slli() {
    super(
      "slli",
      "slli rd, rs1, shamt",
      "set rd = rs1 << shamt, logical shift left"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b001;
    this.funct7 = 0b0000000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 << (imm & 0x1f);
  }

}
