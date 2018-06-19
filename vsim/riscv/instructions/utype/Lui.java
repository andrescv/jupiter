package vsim.riscv.instructions.utype;


public final class Lui extends UType {

  public Lui() {
    super(
      "lui",
      "lui rd, imm",
      "set rd = imm << 12"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0110111;
  }

  @Override
  protected int compute(int imm) {
    return imm << 12;
  }

}
