package vsim.riscv.instructions.stype;

import vsim.Globals;


public final class Sb extends SType {

  public Sb() {
    super(
      "sb",
      "sb rs2, offset(rs1)",
      "set memory[rs1 + sext(offset)] = rs2[7:0]"
    );
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    Globals.memory.storeByte(rs1 + imm, rs2);
  }

}
