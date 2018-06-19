package vsim.riscv.instructions.itype;


public final class FenceI extends IType {

  public FenceI() {
    super(
      "fence.i",
      "fence.i",
      "renders stores to instruction memory observable to subsequent instruction fetches"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0001111;
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  protected int compute(int rs1, int imm) {
    /* DO NOTHING */
    return 0;
  }

}
