package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lhu extends IType {

  public Lhu() {
    super(
      "lhu",
      "lhu rd, offset(rs1)",
      "set rd = memory[rs1 + sext(offset)][15:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0000011;
  }

  @Override
  public int getFunct3() {
    return 0b101;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadHalfUnsigned(rs1 + imm);
  }

}
