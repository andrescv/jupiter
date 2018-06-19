package vsim.riscv.instructions.itype;


public final class Fence extends IType {

  public Fence() {
    super(
      "fence",
      "fence",
      "used to order device I/O and memory accesses as viewed by other RISC-V harts, external devices or coprocessors"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0001111;
  }

  @Override
  protected int compute(int rs1, int imm) {
    /* DO NOTHING */
    return 0;
  }

}
