package vsim.riscv.instructions.rtype;


public final class Slt extends RType {

  public Slt() {
    super(
      "slt",
      "slt rd, rs1, rs2",
      "set rd = 1 if rs1 < rs2 else 0, signed comparison"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b010;
    this.funct7 = 0b0000000;
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return (rs1 < rs2) ? 1 : 0;
  }

}
