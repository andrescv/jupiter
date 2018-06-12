package vsim.riscv.instructions;

import vsim.utils.Colorize;


public final class MachineCode {

  private int code;

  public MachineCode() {
    this(0x00000000);
  }

  public MachineCode(int code) {
    this.code = code;
  }

  public int get(InstructionField field) {
    return (this.code & (field.mask << field.lo)) >>> field.lo;
  }

  public void set(InstructionField field, int value) {
    this.code = (this.code & ~(field.mask << field.lo)) | ((value & field.mask) << field.lo);
  }

  @Override
  public String toString() {
    return Colorize.green(
      String.format(
        "0x%08x",
        this.code
      )
    );
  }

}
