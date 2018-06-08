package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulh extends RType {

  public Mulh() {
    super(
      "mulh",
      "mulh rd, rs1, rs2",
      "set rd = High XLEN bits of rs1 * rs2"
    );
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.mulh(rs1, rs2);
  }

}
