package vsim.riscv;

import vsim.utils.Colorize;
import java.util.Hashtable;


public final class RVFRegisterFile {

  private static final String[] MNEMONICS = {
    "ft0", "ft1", "ft2", "ft3",
    "ft4", "ft5", "ft6", "ft7",
    "fs0", "fs1", "fa0", "fa1",
    "fa2", "fa3", "fa4", "fa5",
    "fa6", "fa7", "fs2", "fs3",
    "fs4", "fs5", "fs6", "fs7",
    "fs8", "fs9", "fs10", "fs11",
    "ft8", "ft9", "ft10", "ft11"
  };

  // only 1 instance
  public static final RVFRegisterFile regfile = new RVFRegisterFile();

  private Hashtable<String, Register> rf;

  private RVFRegisterFile() {
    this.rf = new Hashtable<String, Register>();
    // add 32 general purpose registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      // all registers are editable
      Register reg = new Register(i, 0, true);
      // abi name
      this.rf.put(MNEMONICS[i], reg);
      // default name f0-f31
      this.rf.put("f" + i, reg);
    }
  }

  public int getRegisterNumber(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getNumber();
    return -1;
  }

  public float getRegister(int number) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      return Float.intBitsToFloat(reg.getValue());
    return 0.0f;
  }

  public float getRegister(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return Float.intBitsToFloat(reg.getValue());
    return 0.0f;
  }

  public int getRegisterInt(int number) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  public int getRegisterInt(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  public void setRegister(int number, float value) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      reg.setValue(Float.floatToRawIntBits(value));
  }

  public void setRegister(String name, float value) {
    Register reg = this.rf.get(name);
    if (reg != null)
      reg.setValue(Float.floatToRawIntBits(value));
  }

  public void setRegisterInt(int number, int value) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      reg.setValue(value);
  }

  public void reset() {
    // reset all 32 registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      this.rf.get("f" + i).reset();
    }
  }

  @Override
  public String toString() {
    String out = "";
    String regfmt = "%s%s [%s] (%s)";
    String newline = System.getProperty("line.separator");
    // include all registers in out string
    for (int i = 0; i < MNEMONICS.length; i++) {
      Register reg = this.rf.get("f" + i);
      out += String.format(
        regfmt,
        Colorize.green("x" + i),
        (i >= 10) ? "" : " ",
        reg.toString(),
        Colorize.purple(MNEMONICS[i])
      ) + newline;
    }
    return out.replaceAll("\\s+$", "");
  }

}
