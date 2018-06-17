package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lw extends IType {

  public Lw() {
    super(
      "lw",
      "lw rd, offset(rs1)",
      "set rd = sext(memory[rs1 + sext(offset)][31:0])"
    );
    // set opcode
    this.opcode = 0b0000011;
    this.funct3 = 0b010;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadWord(rs1 + imm);
  }

}
