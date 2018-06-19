package vsim.riscv.instructions.rtype;

import vsim.utils.ALU;


public final class Fmaxs extends FRType {

  public Fmaxs() {
    super(
      "fmax.s",
      "fmax.s frd, frs1, frs2",
      "set frd = max(frs1, frs2)"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00101;
    this.funct3 = 0b001;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return ALU.fmax(rs1, rs2);
  }

}
