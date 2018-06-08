package vsim.riscv.instructions.itype;


public final class FenceI extends IType {

  public FenceI() {
    super(
      "fence.i",
      "fence.i",
      "used to synchronize the instruction and data streams"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return 0;
  }

}
