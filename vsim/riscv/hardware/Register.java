package vsim.riscv.hardware;

import vsim.utils.Colorize;


public final class Register {

    private int number;
    private int value;
    private int resetValue;
    private String name;
    private boolean editable;

    public Register(int number, int value, String name, boolean editable) {
        this.number = number;
        this.value = value;
        this.resetValue = value;
        this.name = name;
        this.editable = editable;
    }

    public int getNumber() {
        return this.number;
    }

    public int getValue() {
        return this.value;
    }

    public int getResetValue() {
        return this.resetValue;
    }

    public String getName() {
        return this.name;
    }

    public boolean isEditable() {
        return this.editable;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setResetValue(int resetValue) {
        this.resetValue = resetValue;
    }

    public void reset() {
        this.value = this.resetValue;
    }

    @Override
    public String toString() {
        return String.format(
            "%s [%s] (%s)",
            Colorize.green(String.format("X%02d", this.number)),
            Colorize.red(String.format("0x%08x", this.value)),
            Colorize.blue(this.name)
        );
    }

}