package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lb extends IType {

  public Lb() {
    super(
      "lb",
      "lb rd, offset(rs1)",
      "set rd = sext(memory[rs1 + sext(offset)][7:0])"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0000011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadByte(rs1 + imm);
  }

}
