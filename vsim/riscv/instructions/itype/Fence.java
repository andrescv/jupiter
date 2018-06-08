package vsim.riscv.instructions.itype;


public final class Fence extends IType {

  public Fence() {
    super(
      "fence",
      "fence",
      "instruction to guarantee ordering between memory operations from different RISC-V harts"
    );
  }

  @Override
  protected int compute(int rs1, int imm) {
    return 0;
  }

}
