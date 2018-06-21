package vsim.riscv.instructions.itype;

import vsim.Settings;


public final class Ebreak extends IType {

  public Ebreak() {
    super(
      "ebreak",
      "ebreak",
      "used by debuggers to cause control to be transferred back to a debugging environment"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1110011;
  }

  @Override
  protected int compute(int rs1, int imm) {
    // activate debugging
    Settings.DEBUG = true;
    return 0;
  }

}
