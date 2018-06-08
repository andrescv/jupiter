package vsim.riscv.instructions.itype;


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
    // TODO: implement environment calls
    return 0;
  }

}
