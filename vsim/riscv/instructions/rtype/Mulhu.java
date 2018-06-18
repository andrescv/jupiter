package vsim.riscv.instructions.rtype;

import vsim.Globals;
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
  protected void compute(int rd, int rs1, int rs2) {
    Globals.regfile.setRegister(
      rd,
      Data.mulhu(Globals.regfile.getRegister(rs1), Globals.regfile.getRegister(rs2))
    );
  }

}
