package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Data;


public final class Sltu extends RType {

  public Sltu() {
    super(
      "sltu",
      "sltu rd, rs1, rs2",
      "set rd = 1 if rs1 < rs2 else 0, unsigned comparison"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b011;
    this.funct7 = 0b0000000;
  }

  @Override
  protected void compute(int rd, int rs1, int rs2) {
    Globals.regfile.setRegister(
      rd,
      Data.ltu(Globals.regfile.getRegister(rs1), Globals.regfile.getRegister(rs2)) ? 1 : 0
    );
  }

}
