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
  }

  @Override
  protected int compute(int rs1, int imm) {
    int which = State.regfile.getRegister("a0");
    // TODO: implement ecalls
    switch (which) {
      case Syscall.PRINT_INT:
        break;
      case Syscall.PRINT_STRING:
        break;
      case Syscall.READ_INT:
        break;
      case Syscall.READ_STRING:
        break;
      case Syscall.SBRK:
        break;
      case Syscall.EXIT:
        break;
      case Syscall.PRINT_CHAR:
        break;
      case Syscall.READ_CHAR:
        break;
      case Syscall.EXIT2:
        break;
    }

    return 0;
  }

}
