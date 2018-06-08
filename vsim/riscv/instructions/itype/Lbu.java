package vsim.riscv.instructions.itype;

import vsim.simulator.State;


public final class Lbu extends IType {

  public Lbu() {
    super(
      "lbu",
      "lbu rd, offset(rs1)",
      "set rd = byte(memory[rs1 + offset])"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return State.memory.loadByteUnsigned(rs1 + imm);
  }

}
