package vsim.riscv;

import vsim.utils.Colorize;
import java.util.Hashtable;
import static vsim.riscv.instructions.Instruction.LENGTH;


public final class RVIRegisterFile {

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
  public static final RVIRegisterFile regfile = new RVIRegisterFile();

  private Hashtable<String, Register> rf;
  private Register pc;

  private RVIRegisterFile() {
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
    this.pc = new Register(32, MemorySegments.TEXT_SEGMENT, true);
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemorySegments.STACK_SEGMENT);
    this.rf.get("gp").setValue(MemorySegments.DATA_SEGMENT);
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

  public void incProgramCounter() {
    this.pc.setValue(this.pc.getValue() + LENGTH);
  }

  public void reset() {
    // reset all 32 registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      this.rf.get("x" + i).reset();
    }
    // use pc default reset value
    this.pc.reset();
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemorySegments.STACK_SEGMENT);
    this.rf.get("gp").setValue(MemorySegments.DATA_SEGMENT);
  }

  public void print() {
    System.out.println(this);
  }

  public void printReg(String name) {
    Register reg = this.rf.get(name);
    if (reg != null) {
      int i = reg.getNumber();
      System.out.println(
        String.format(
          "%s [%s] (%s) {= %d}",
          Colorize.green("x" + i),
          reg.toString(),
          Colorize.purple(MNEMONICS[i]),
          reg.getValue()
        )
      );
    } else if (name.equals("pc"))
      System.out.println(
        String.format(
          "PC  [%s]",
          this.pc.toString()
        )
      );
  }

  @Override
  public String toString() {
    String out = "";
    String regfmt = "%s%s [%s] (%s)%s{= %d}";
    String newline = System.getProperty("line.separator");
    // include all registers in out string
    for (int i = 0; i < MNEMONICS.length; i++) {
      Register reg = this.rf.get("x" + i);
      out += String.format(
        regfmt,
        Colorize.green("x" + i),
        (i >= 10) ? "" : " ",
        reg.toString(),
        Colorize.purple(MNEMONICS[i]),
        (MNEMONICS[i].length() == 5) ?
          " " : (MNEMONICS[i].length() == 2) ?
          "    " : (MNEMONICS[i].length() == 3) ?
          "   " : "  ",
        reg.getValue()
      ) + newline;
    }
    // and pc
    out += newline + String.format(
      "PC  [%s]",
      this.pc.toString()
    );
    return out;
  }

}
