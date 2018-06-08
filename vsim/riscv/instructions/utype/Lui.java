package vsim.riscv.instructions.utype;


public final class Lui extends UType {

  public Lui() {
    super(
      "lui",
      "lui rd, imm",
      "set rd[31:13] = imm"
    );
  }

  @Override
  protected int compute(int imm) {
    return (imm << 20) & 0xfffff000;
  }

}
