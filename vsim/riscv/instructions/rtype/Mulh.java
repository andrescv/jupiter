package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulh extends RType {

  public Mulh() {
    super(
      "mulh",
      "mulh rd, rs1, rs2",
      "set rd = (rs1 * rs2) >> XLEN"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b001;
    this.funct7 = 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.mulh(rs1, rs2);
  }

}
