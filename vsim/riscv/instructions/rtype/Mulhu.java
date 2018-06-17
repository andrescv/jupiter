package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulhu extends RType {

  public Mulhu() {
    super(
      "mulhu",
      "mulhu rd, rs1, rs2",
      "set rd = (unsigned(rs1) * unsigned(rs2)) >>> XLEN"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b011;
    this.funct7 = 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.mulhu(rs1, rs2);
  }

}
