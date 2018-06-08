package vsim.riscv.hardware;

import vsim.utils.Colorize;


final class Register {

  private int value;
  private int resetValue;
  private boolean editable;

  protected Register(int value, boolean editable) {
    this.value = value;
    this.resetValue = value;
    this.editable = editable;
  }

  protected int getValue() {
    return this.value;
  }

  protected int getResetValue() {
    return this.resetValue;
  }

  protected boolean isEditable() {
    return this.editable;
  }

  protected void setValue(int value) {
    if (this.editable)
      this.value = value;
  }

  protected void setResetValue(int resetValue) {
    if (this.editable)
      this.resetValue = resetValue;
  }

  protected void reset() {
    this.value = this.resetValue;
  }

  @Override
  public String toString() {
    return Colorize.blue(String.format("0x%08x", this.value));
  }

}
