package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Lhu extends IType {

  public Lhu() {
    super(
      "lhu",
      "lhu rd, offset(rs1)",
      "set rd = half(memory[rs1 + offset])"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return State.memory.loadHalfUnsigned(rs1 + imm);
  }

}
