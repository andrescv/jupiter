package vsim.riscv.instructions.rtype;

import vsim.utils.Data;


public final class Remu extends RType {

  public Remu() {
    super(
      "remu",
      "remu rd, rs1, rs2",
      "set rd = rs1 unsigned(%) rs2"
    );
    // set opcode
    this.opcode = 0b0110011;
    this.funct3 = 0b111;
    this.funct7 = 0b0000001;
  }

  @Override
  protected int compute(int rs1, int rs2) {
    return Data.remu(rs1, rs2);
  }

}
