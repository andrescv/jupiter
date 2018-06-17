package vsim.riscv.instructions.utype;

import vsim.Globals;


public final class Auipc extends UType {

  public Auipc() {
    super(
      "auipc",
      "auipc rd, imm",
      "set rd = imm << 12 + pc"
    );
    // set opcode
    this.opcode = 0b0010111;
  }

  @Override
  protected int compute(int imm) {
    return (imm << 12) + Globals.regfile.getProgramCounter();
  }

}
