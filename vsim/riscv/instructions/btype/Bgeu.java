package vsim.riscv.instructions.btype;

import vsim.utils.ALU;


public final class Bgeu extends BType {

  public Bgeu() {
    super(
      "bgeu",
      "bgeu rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 >= rs2, unsigned comparison"
    );
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return ALU.geu(rs1, rs2);
  }

}
