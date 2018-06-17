package vsim.riscv.instructions.utype;


public final class Lui extends UType {

  public Lui() {
    super(
      "lui",
      "lui rd, imm",
      "set rd = imm << 12"
    );
    // set opcode
    this.opcode = 0b0110111;
  }

  @Override
  protected int compute(int imm) {
    return imm << 12;
  }

}
