package vsim.riscv.instructions.itype;


public final class EBreak extends IType {

  public EBreak() {
    super(
      "ebreak",
      "ebreak",
      "used by debuggers to cause control to be transferred back to a debugging environment"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    // TODO: implement environment break
    return 0;
  }

}
