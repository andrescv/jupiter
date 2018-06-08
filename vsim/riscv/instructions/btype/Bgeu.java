package vsim.riscv.instructions.btype;

import vsim.utils.Data;


public final class Bgeu extends BType {

  public Bgeu() {
    super(
      "bgeu",
      "bgeu rs1, rs2, imm",
      "set pc = pc + imm if rs1 >= rs2, unsigned comparison"
    );
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return Data.geu(rs1, rs2);
  }

}
