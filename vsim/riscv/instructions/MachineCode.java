package vsim.riscv.instructions;


public final class MachineCode {

  private int code;

  public MachineCode(int code) {
    this.code = code;
  }

  public int get(InstructionField field) {
    return (this.code & (field.mask << field.lo)) >>> field.lo;
  }

  public void set(InstructionField field, int value) {
    this.code = (this.code & ~(field.mask << field.lo)) | ((value & field.mask) << field.lo);
  }

}
