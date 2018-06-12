package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lhu extends IType {

  public Lhu() {
    super(
      "lhu",
      "lhu rd, offset(rs1)",
      "set rd = half(memory[rs1 + offset])"
    );
    // set opcode
    this.opcode = 0b0000011;
    this.funct3 = 0b101;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadHalfUnsigned(rs1 + imm);
  }

}
