package vsim.riscv.instructions.stype;

import vsim.simulator.State;


public final class Sb extends SType {

  public Sb() {
    super(
      "sb",
      "sb rs2, offset(rs1)",
      "set memory[rs1 + offset] = rs2[0:7]"
    );
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    State.memory.storeByte(rs1 + imm, rs2);
  }

}
