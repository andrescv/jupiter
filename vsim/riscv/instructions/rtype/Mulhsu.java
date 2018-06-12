package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Mulhsu extends RType {

  public Mulhsu() {
    super(
      "mulhsu",
      "mulhsu rd, rs1, rs2",
      "set rd = High XLEN bits of rs1 * unsigned(rs2)"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b010;
    this.funct7 = 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.mulhsu(rs1, rs2);
  }

}
