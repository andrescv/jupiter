package vsim.riscv.instructions.itype;


public final class FenceI extends IType {

  public FenceI() {
    super(
      "fence.i",
      "fence.i",
      "renders stores to instruction memory observable to subsequent instruction fetches"
    );
    // set opcode
    this.opcode = 0b0001111;
    this.funct3 = 0b001;
  }

  @Override
  protected int compute(int rs1, int imm) {
    /* DO NOTHING */
    return 0;
  }

}
