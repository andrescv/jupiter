package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Jalr extends IType {

  public Jalr() {
    super(
      "jalr",
      "jalr rd, imm",
      "set rd = pc + 4 and pc = pc + ((rs1 + imm) & ~0x1)"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    int pc = State.regfile.getProgramCounter();
    // set the least-significant bit of the result to zero
    State.regfile.setProgramCounter(((rs1 + imm) >> 1) << 1);
    return pc + 4;
  }

}
