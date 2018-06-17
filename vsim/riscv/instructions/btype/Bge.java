package vsim.riscv.instructions.btype;


public final class Bge extends BType {

  public Bge() {
    super(
      "bge",
      "bge rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 >= rs2, signed comparison"
    );
    // set opcode
    this.opcode = 0b1100011;
    this.funct3 = 0b101;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 >= rs2;
  }

}
