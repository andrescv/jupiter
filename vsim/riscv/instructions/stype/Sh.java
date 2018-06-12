package vsim.riscv.instructions.stype;

import vsim.simulator.State;


public final class Sh extends SType {

  public Sh() {
    super(
      "sh",
      "sh rs2, offset(rs1)",
      "set memory[rs1 + offset] = rs2[0:15]"
    );
    // set opcode
    this.opcode = 0b0100011;
    this.funct3 = 0b001;
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    State.memory.storeHalf(rs1 + imm, rs2);
  }

}
