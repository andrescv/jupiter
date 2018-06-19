package vsim.riscv.instructions.frtype;


public final class Fmuls extends FRType {

  public Fmuls() {
    super(
      "fmul.s",
      "fmul.s frd, frs1, frs2",
      "set frd = frs1 * frs2"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00010;
    this.funct3 = 0b111;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return rs1 * rs2;
  }

}
