package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fclasss extends Instruction {

  public Fclasss() {
    super(
      Instruction.Format.R,
      "fclass.s",
      "fclass.s rd, frs1",
      "set rd = 10-bit mask that indicates the class of the floating-point number"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct5() {
    return 0b11100;
  }

  @Override
  public void execute(MachineCode code) {
    int out = 0;
    float f = Globals.fregfile.getRegister(code.get(InstructionField.RS1));
    int bits = Float.floatToRawIntBits(f);
    // set flags
    boolean infOrNaN = Float.isNaN(f) || Float.isInfinite(f);
    boolean subnormalOrZero = (bits & Data.EXPONENT_MASK) == 0;
    boolean sign = ((bits & Data.SIGN_MASK) >> 31) != 0;
    boolean fracZero = (bits & Data.FRACTION_MASK) == 0;
    boolean isNaN = Float.isNaN(f);
    boolean isSNaN = Data.signalingNaN(f);
    // build 10-bit mask
    if (sign && infOrNaN && fracZero)
      out |= 1 << 0;
    if (sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 1;
    if (sign && subnormalOrZero && !fracZero)
      out |= 1 << 2;
    if (sign && subnormalOrZero && fracZero)
      out |= 1 << 3;
    if (!sign && infOrNaN && fracZero)
      out |= 1 << 7;
    if (!sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 6;
    if (!sign && subnormalOrZero && !fracZero)
      out |= 1 << 5;
    if (!sign && subnormalOrZero && fracZero)
      out |= 1 << 4;
    if (isNaN &&  isSNaN)
      out |= 1 << 8;
    if (isNaN && !isSNaN)
      out |= 1 << 9;
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      out
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return Colorize.cyan(String.format("%s x%d, f%d", op, rd, rs1));
  }

}
