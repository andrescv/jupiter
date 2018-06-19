package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Fsgnjns extends FRType {

  public Fsgnjns() {
    super(
      "fsgnjn.s",
      "fsgnjn.s frd, frs1, frs2",
      "set frd = {~frs2[31], frs1[30:0]}"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00100;
    this.funct3 = 0b001;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fsgnjn(rs1, rs2);
  }

}
