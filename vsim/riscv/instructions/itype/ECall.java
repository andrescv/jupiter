package vsim.riscv.instructions.itype;

import vsim.utils.Syscall;
import vsim.simulator.State;


public final class ECall extends IType {

  public ECall() {
    super(
      "ecall",
      "ecall",
      "used to make a request to the supporting execution environment"
    );
    // set opcode
    this.opcode = 0b1110011;
    this.funct3 = 0b000;
  }

  @Override
  protected int compute(int rs1, int imm) {
    // call syscall handler
    Syscall.handler();
    return 0;
  }

}
