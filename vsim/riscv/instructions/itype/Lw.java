package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Lw extends IType {

  public Lw() {
    super(
      "lw",
      "lw rd, offset(rs1)",
      "set rd = word(memory[rs1 + offset])"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return State.memory.loadWord(rs1 + imm);
  }

}
