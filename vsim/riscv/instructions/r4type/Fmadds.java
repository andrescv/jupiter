package vsim.riscv.instructions.r4type;


public final class Fmadds extends FR4Type {

  public Fmadds() {
    super(
      "fmadd.s",
      "fmadd.s frd, frs1, frs2, frs3",
      "set frd = frs1 * frs2 + frs3"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1000011;
  }

  @Override
  public float compute(float rs1, float rs2, float rs3) {
    return (rs1 * rs2) + rs3;
  }

}
