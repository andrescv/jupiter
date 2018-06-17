package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lh extends IType {

  public Lh() {
    super(
      "lh",
      "lh rd, offset(rs1)",
      "set rd = sext(memory[rs1 + sext(offset)][15:0])"
    );
    // set opcode
    this.opcode = 0b0000011;
    this.funct3 = 0b001;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadHalf(rs1 + imm);
  }

}
