package vsim.riscv.instructions.itype;


public final class Addi extends IType {

  public Addi() {
    super(
      "addi",
      "addi rd, rs1, imm",
      "set rd = rs1 + imm, overflow is ignored"
    );
    // set opcode
    this.opcode = 0b0010011;
    this.funct3 = 0b000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return rs1 + imm;
  }

}
