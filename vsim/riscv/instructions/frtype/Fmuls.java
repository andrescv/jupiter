package vsim.riscv.instructions.frtype;


public final class Fmuls extends FRType {

  public Fmuls() {
    super(
      "fadd.s",
      "fadd.s frd, frs1, frs2",
      "set frd = frs1 * frs2"
    );
  }

  @Override
  public float compute(float rs1, float rs2) {
    return rs1 * rs2;
  }

}
