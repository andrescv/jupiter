package vsim.riscv.instructions;


public final class InstructionField {

  // all bits
  public static final InstructionField ALL       = new InstructionField(0, 31);
  // opcode
  public static final InstructionField OPCODE    = new InstructionField(0, 6);
  // functions
  public static final InstructionField FUNCT3    = new InstructionField(12, 14);
  public static final InstructionField FUNCT5    = new InstructionField(27, 31);
  public static final InstructionField FUNCT7    = new InstructionField(25, 31);
  // registers
  public static final InstructionField RD        = new InstructionField(7, 11);
  public static final InstructionField RS1       = new InstructionField(15, 19);
  public static final InstructionField RS2       = new InstructionField(20, 24);
  public static final InstructionField RS3       = new InstructionField(27, 31);
  // shift amount
  public static final InstructionField SHAMT     = new InstructionField(20, 24);
  // immediates
  public static final InstructionField IMM_11_0  = new InstructionField(20, 31);
  public static final InstructionField IMM_4_0   = new InstructionField(7, 11);
  public static final InstructionField IMM_11_5  = new InstructionField(25, 31);
  public static final InstructionField IMM_11B   = new InstructionField(7);
  public static final InstructionField IMM_4_1   = new InstructionField(8, 11);
  public static final InstructionField IMM_10_5  = new InstructionField(25, 30);
  public static final InstructionField IMM_12    = new InstructionField(31);
  public static final InstructionField IMM_31_12 = new InstructionField(12, 31);
  public static final InstructionField IMM_19_12 = new InstructionField(12, 19);
  public static final InstructionField IMM_11J   = new InstructionField(20);
  public static final InstructionField IMM_10_1  = new InstructionField(21, 30);
  public static final InstructionField IMM_20    = new InstructionField(31);
  // rounding method
  public static final InstructionField RM        = new InstructionField(12, 14);
  // format
  public static final InstructionField FMT       = new InstructionField(25, 26);

  protected int lo;
  protected int hi;
  protected int mask;
  protected int length;

  private InstructionField(int lo) {
    this(lo, lo);
  }

  private InstructionField(int lo, int hi) {
    this.lo = lo;
    this.hi = hi;
    this.mask = 0;
    this.length = hi - lo + 1;
    for (int i = lo, j = 0; i <= hi; i++, j++)
      this.mask |= 1 << j;
  }

  @Override
  public String toString() {
    return String.format(
      "Field<lo: %d, hi: %d, length: %d, mask: %s>",
      this.lo,
      this.hi,
      this.length,
      Integer.toBinaryString(this.mask)
    );
  }

}
