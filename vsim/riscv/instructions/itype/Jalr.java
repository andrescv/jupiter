package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Jalr extends IType {

  public Jalr() {
    super(
      "jalr",
      "jalr rd, offset",
      "set rd = pc + 4 and pc = pc + ((rs1 + sext(offset)) & ~1)"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1100111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    int pc = Globals.regfile.getProgramCounter();
    // set the least-significant bit of the result to zero
    Globals.regfile.setProgramCounter((rs1 + imm) & ~1);
    return pc + 4;
  }

}
