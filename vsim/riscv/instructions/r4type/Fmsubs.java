package vsim.riscv.instructions.r4type;


public final class Fmsubs extends FR4Type {

  public Fmsubs() {
    super(
      "fmsub.s",
      "fmsub.s frd, frs1, frs2, frs3",
      "set frd = frs1 * frs2 - frs3"
    );
    // set opcode
    this.opcode = 0b1000111;
  }

  @Override
  public float compute(float rs1, float rs2, float rs3) {
    return (rs1 * rs2) - rs3;
  }

}
