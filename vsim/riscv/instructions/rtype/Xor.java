package vsim.riscv.instructions.rtype;


public final class Xor extends RType {

  public Xor() {
    super(
      "xor",
      "xor rd, rs1, rs2",
      "set rd = rs1 ^ rs2, bitwise xor"
    );
}

  @Override
  protected int compute(int rs1, int rs2) {
    return rs1 ^ rs2;
  }

}
