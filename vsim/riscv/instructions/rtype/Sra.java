package vsim.riscv.instructions.rtype;

import vsim.Globals;


public final class Sra extends RType {

  public Sra() {
    super(
      "sra",
      "sra rd, rs1, rs2",
      "set rd = rs1 >> rs2[4:0], arithmetic shift right"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b101;
    this.funct7 = 0b0100000;
  }

  @Override
  protected void compute(int rd, int rs1, int rs2) {
    Globals.regfile.setRegister(
      rd,
      Globals.regfile.getRegister(rs1) >> (Globals.regfile.getRegister(rs2) & 0x1f)
    );
  }

}
