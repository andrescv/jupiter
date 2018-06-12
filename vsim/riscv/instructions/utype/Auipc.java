package vsim.riscv.instructions.utype;

import vsim.simulator.State;


public final class Auipc extends UType {

  public Auipc() {
    super(
      "auipc",
      "auipc rd, imm",
      "set rd = (imm << 20) + pc"
    );
    // set opcode
    this.opcode = 0b0010111;
  }

  @Override
  protected int compute(int imm) {
    return ((imm << 20) & 0xfffff000) + State.regfile.getProgramCounter();
  }

}
