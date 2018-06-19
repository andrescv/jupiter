package vsim.riscv.instructions.rtype;


public final class Mul extends RType {

  public Mul() {
    super(
      "mul",
      "mul rd, rs1, rs2",
      "set rd = rs1 * rs2, overflow is ignored"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b000;
    this.funct7 = 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 * rs2;
  }

}
