package vsim.riscv.instructions.stype;

import vsim.Globals;


public final class Sh extends SType {

  public Sh() {
    super(
      "sh",
      "sh rs2, offset(rs1)",
      "set memory[rs1 + sext(offset)] = rs2[15:0]"
    );
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  protected void setMemory(int rs1, int rs2, int imm) {
    Globals.memory.storeHalf(rs1 + imm, rs2);
  }

}
