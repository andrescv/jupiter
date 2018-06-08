package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Lh extends IType {

  public Lh() {
    super(
      "lh",
      "lh rd, offset(rs1)",
      "set rd = signExtend(half(memory[rs1 + offset]))"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return State.memory.loadHalf(rs1 + imm);
  }

}
