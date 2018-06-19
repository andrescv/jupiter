package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsqrts extends FRType {

  public Fsqrts() {
    super(
      "fsqrt.s",
      "fsqrt.s frd, frs1",
      "set frd = sqrt(frs1)"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b01011;
    this.funct3 = 0b111;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.sqrt(rs1);
  }

}
