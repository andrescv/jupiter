package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lbu extends IType {

  public Lbu() {
    super(
      "lbu",
      "lbu rd, offset(rs1)",
      "set rd = memory[rs1 + sext(offset)][7:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0000011;
  }

  @Override
  public int getFunct3() {
    return 0b100;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadByteUnsigned(rs1 + imm);
  }

}
