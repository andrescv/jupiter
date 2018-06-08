package vsim.riscv.instructions.stype;

import vsim.simulator.State;


public final class Sw extends SType {

  public Sw() {
    super(
      "sw",
      "sw rs2, offset(rs1)",
      "set memory[rs1 + offset] = rs2"
    );
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    State.memory.storeWord(rs1 + imm, rs2);
  }

}
