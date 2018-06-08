package vsim.riscv;


final class Register {

  private int number;
  private int value;
  private int resetValue;
  private boolean editable;

  protected Register(int number, int value, boolean editable) {
    this.number = number;
    this.value = value;
    this.resetValue = value;
    this.editable = editable;
  }

  protected int getNumber() {
    return this.number;
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

}
