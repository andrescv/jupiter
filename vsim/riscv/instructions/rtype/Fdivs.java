package vsim.riscv.instructions.rtype;


public final class Fdivs extends FRType {

  public Fdivs() {
    super(
      "fdiv.s",
      "fdiv.s frd, frs1, frs2",
      "set frd = frs1 / frs2"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00011;
    this.funct3 = 0b111;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return rs1 / rs2;
  }

}
