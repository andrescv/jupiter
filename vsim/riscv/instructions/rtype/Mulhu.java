package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulhu extends RType {

  public Mulhu() {
    super(
      "mulhu",
      "mulhu rd, rs1, rs2",
      "set rd = High XLEN bits of unsigned(rs1) * unsigned(rs2)"
    );
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.mulhu(rs1, rs2);
  }

}
