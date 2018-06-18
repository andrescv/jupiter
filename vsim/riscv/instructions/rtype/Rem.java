package vsim.riscv.instructions.rtype;

import vsim.Globals;


public final class Rem extends RType {

  public Rem() {
    super(
      "rem",
      "rem rd, rs1, rs2",
      "set rd = rs1 % rs2"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b110;
    this.funct7 = 0b0000001;
  }

  @Override
  protected void compute(int rd, int rs1, int rs2) {
    Globals.regfile.setRegister(
      rd,
      Globals.regfile.getRegister(rs1) % Globals.regfile.getRegister(rs2)
    );
  }

}
