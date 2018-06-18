package vsim.riscv.instructions.rtype;

import vsim.Globals;


public final class Sll extends RType {

  public Sll() {
    super(
      "sll",
      "sll rd, rs1, rs2",
      "set rd = rs1 << rs2[4:0], logical shift left"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b001;
    this.funct7 = 0b0000000;
  }

  @Override
  protected void compute(int rd, int rs1, int rs2) {
    Globals.regfile.setRegister(
      rd,
      Globals.regfile.getRegister(rs1) << (Globals.regfile.getRegister(rs2) & 0x1f)
    );
  }

}
