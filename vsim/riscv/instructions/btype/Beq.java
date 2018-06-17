package vsim.riscv.instructions.btype;


public final class Beq extends BType {

  public Beq() {
    super(
      "beq",
      "beq rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 == rs2"
    );
    // set opcode
    this.opcode = 0b1100011;
    this.funct3 = 0b000;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return rs1 == rs2;
  }

}
