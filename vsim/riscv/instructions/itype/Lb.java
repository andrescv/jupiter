package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Lb extends IType {

  public Lb() {
    super(
      "lb",
      "lb rd, offset(rs1)",
      "set rd = signExtend(byte(memory[rs1 + offset]))"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return State.memory.loadByte(rs1 + imm);
  }

}
