package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Fsgnjs extends FRType {

  public Fsgnjs() {
    super(
      "fsgnj.s",
      "fsgnj.s frd, frs1, frs2",
      "set frd = {frs2[31], frs1[30:0]}"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00100;
    this.funct3 = 0b000;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return ALU.fsgnj(rs1, rs2);
  }

}
