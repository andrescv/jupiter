package vsim.riscv.instructions.rtype;


public final class Fsubs extends FRType {

  public Fsubs() {
    super(
      "fsub.s",
      "fsub.s frd, frs1, frs2",
      "set frd = frs1 - frs2"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct5() {
    return 0b00001;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return rs1 - rs2;
  }

}
