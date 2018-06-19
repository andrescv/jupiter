package vsim.riscv.instructions.itype;

import vsim.utils.Syscall;


public final class ECall extends IType {

  public ECall() {
    super(
      "ecall",
      "ecall",
      "makes a request to the supporting execution environment"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1110011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    // call syscall handler
    Syscall.handler();
    return 0;
  }

}
