package vsim.riscv.instructions.frtype;

import vsim.utils.Data;


public final class Fsgnjxs extends FRType {

  public Fsgnjxs() {
    super(
      "fsgnjx.s",
      "fsgnjx.s frd, frs1, frs2",
      "set frd = {frs1[31] ^ frs2[31], frs1[30:0]}"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b00100;
    this.funct3 = 0b010;
  }

  @Override
  public float compute(float rs1, float rs2) {
    return Data.fsgnjx(rs1, rs2);
  }

}
