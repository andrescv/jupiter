package vsim.riscv.instructions.itype;

import vsim.utils.Data;


public final class Sltiu extends IType {

  public Sltiu() {
    super(
      "sltiu",
      "sltiu rd, rs1, imm",
      "set rd = 1 if rs1 < imm else 0, unsigned comparison"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Data.ltu(rs1, imm) ? 1 : 0;
  }

}
