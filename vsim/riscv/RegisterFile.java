package vsim.riscv;

import vsim.utils.Colorize;
import java.util.Hashtable;


public final class RegisterFile {

  private static final String[] MNEMONICS = {
    "zero", "ra", "sp", "gp",
    "tp", "t0", "t1", "t2",
    "s0/fp", "s1", "a0", "a1",
    "a2", "a3", "a4", "a5",
    "a6", "a7", "s2", "s3",
    "s4", "s5", "s6", "s7",
    "s8", "s9", "s10", "s11",
    "t3", "t4", "t5", "t6"
  };

  // only 1 instance
  public static final RegisterFile regfile = new RegisterFile();

  private final class Register {

    private int number;
    private int value;
    private int resetValue;
    private boolean editable;

    private Register(int number, int value, boolean editable) {
      this.number = number;
      this.value = value;
      this.resetValue = value;
      this.editable = editable;
    }

    private int getNumber() {
      return this.number;
    }

    private int getValue() {
      return this.value;
    }

    private void setValue(int value) {
      if (this.editable)
        this.value = value;
    }

    private void setResetValue(int resetValue) {
      if (this.editable)
        this.resetValue = resetValue;
    }

    private void reset() {
      this.value = this.resetValue;
    }

  }

  private Hashtable<String, Register> rf;
  private Register pc;

  private RegisterFile() {
    this.rf = new Hashtable<String, Register>();
    // add 32 general purpose registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      // note: only "zero" register is not editable
      Register reg = new Register(i, 0, i != 0);
      // point all names to this new register in Hashtable
      String[] names = MNEMONICS[i].split("/");
      for (int j = 0; j < names.length; j++)
        this.rf.put(names[j], reg);
      // default name x[0-9]+
      this.rf.put("x" + i, reg);
    }
    // program counter is a special register
    this.pc = new Register(32, MemoryConfig.TEXT_SEGMENT, true);
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemoryConfig.STACK_SEGMENT);
    this.rf.get("gp").setValue(MemoryConfig.DATA_SEGMENT);
  }

  public int getRegisterNumber(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getNumber();
    return -1;
  }

  public int getRegister(int number) {
    Register reg = this.rf.get("x" + number);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  public int getRegister(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  public int getProgramCounter() {
    return this.pc.getValue();
  }

  public void setRegister(int number, int value) {
    Register reg = this.rf.get("x" + number);
    if (reg != null)
      reg.setValue(value);
  }

  public void setRegister(String name, int value) {
    Register reg = this.rf.get(name);
    if (reg != null)
      reg.setValue(value);
  }

  public void setProgramCounter(int value) {
    this.pc.setValue(value);
  }

  public void reset() {
    // reset all 32 registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      this.rf.get("x" + i).reset();
    }
    // use pc default reset value
    this.pc.reset();
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemoryConfig.STACK_SEGMENT);
    this.rf.get("gp").setValue(MemoryConfig.DATA_SEGMENT);
  }

  @Override
  public String toString() {
    String out = "";
    String regfmt = "%s%s [%s] (%s)";
    String newline = System.getProperty("line.separator");
    // include all registers in out string
    for (int i = 0; i < MNEMONICS.length; i++) {
      Register reg = this.rf.get("x" + i);
      out += String.format(
        regfmt,
        Colorize.green("x" + i),
        (i >= 10) ? "" : " ",
        Colorize.blue(String.format("0x%08x", reg.getValue())),
        Colorize.purple(MNEMONICS[i])
      ) + newline;
    }
    // and pc
    out += newline + String.format(
      "PC  [%s]",
      Colorize.blue(String.format("0x%08x", this.pc.getValue()))
    );
    return out;
  }

}
