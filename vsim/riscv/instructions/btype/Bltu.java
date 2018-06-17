package vsim.riscv.instructions.btype;

import vsim.utils.Data;


public final class Bltu extends BType {

  public Bltu() {
    super(
      "bltu",
      "bltu rs1, rs2, offset",
      "set pc = pc + sext(offset), if rs1 < rs2, unsigned comparison"
    );
    // set opcode
    this.opcode = 0b1100011;
    this.funct3 = 0b110;
  }

  @Override
  protected boolean comparison(int rs1, int rs2) {
    return Data.ltu(rs1, rs2);
  }

}
