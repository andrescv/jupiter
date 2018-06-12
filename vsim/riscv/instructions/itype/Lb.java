package vsim.riscv.instructions.itype;

import vsim.Globals;


public final class Lb extends IType {

  public Lb() {
    super(
      "lb",
      "lb rd, offset(rs1)",
      "set rd = signExtend(byte(memory[rs1 + offset]))"
    );
    // set opcode
    this.opcode = 0b0000011;
    this.funct3 = 0b000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    return Globals.memory.loadByte(rs1 + imm);
  }

}
