/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package jupiter.riscv.hardware;

import jupiter.utils.Data;
import jupiter.utils.IO;


/** Register file for the I extension. */
public final class RVIRegisterFile extends RegisterFile {

  // register file mnemonics
  private static final String[] mnemonics = {
    "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2", "s0", "s1", "a0", "a1", "a2",
    "a3", "a4", "a5", "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10",
    "s11", "t3", "t4", "t5", "t6"
  };

  /** program counter special register */
  private final Register pc;

  /** Creates a RVI register file. */
  public RVIRegisterFile() {
    super(32, "x");
    // add 32 general purpose registers
    for (int i = 0; i < mnemonics.length; i++) {
      // default reset value
      int resetValue = 0;
      // default reset value for stack pointer
      if (mnemonics[i].equals("sp"))
        resetValue = Data.STACK_POINTER;
      // default reset value for global pointer
      if (mnemonics[i].equals("gp"))
        resetValue = Data.GLOBAL_POINTER;
      // note: only "zero" register is not editable
      Register reg = new Register(i, mnemonics[i], resetValue, i != 0);
      // point all names to this new register
      rf.put(mnemonics[i], reg);
      rf.put(prefix + i, reg);
      // special frame pointer
      if (mnemonics[i].equals("s0"))
        rf.put("fp", reg);
    }
    // create program counter
    pc = new Register(32, "pc", Data.TEXT, true);
  }

  /**
   * Gets the value of the program counter register.
   *
   * @return program counter value
   */
  public int getProgramCounter() {
    return pc.getValue();
  }

  /**
   * Sets the value of the program counter register.
   *
   * @param value program counter new value
   */
  public void setProgramCounter(int value) {
    pc.setValue(value);
  }

  /** Increments program counter by {@link jupiter.utils.Data#WORD_LENGTH}. */
  public void incProgramCounter() {
    pc.setValue(this.pc.getValue() + Data.WORD_LENGTH);
  }

  /** {@inheritDoc} */
  @Override
  public void reset() {
    super.reset();
    pc.reset();
  }

  /** {@inheritDoc} */
  @Override
  public void print() {
    String fmt = "%s%s (%s)%s[0x%08x] {= %d}";
    // include all registers in out string
    for (int i = 0; i < size; i++) {
      Register reg = rf.get(prefix + i);
      String number = prefix + i;
      String space1 = (i >= 10) ? "" : " ";
      int length = reg.getMnemonic().length();
      String space2 = (length == 4) ? " " : ((length == 3) ? "  " : "   ");
      String mnemonic = reg.getMnemonic();
      IO.stdout().println(String.format(fmt, number, space1, mnemonic, space2, reg.getValue(), reg.getValue()));
    }
    // and pc
    IO.stdout().println();
    IO.stdout().println(String.format("PC         [0x%08x] {= %d}", pc.getValue(), pc.getValue()));
  }

  /** {@inheritDoc} */
  @Override
  public void print(String name) {
    Register reg = null;
    if ("pc".equals(name))
      reg = pc;
    else
      reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    // print regular register
    if (!("pc".equals(reg.getMnemonic()))) {
      String fmt = "%s (%s) [0x%08x] {= %d}";
      String number = prefix + reg.getNumber();
      String mnemonic = reg.getMnemonic();
      IO.stdout().println(String.format(fmt, number, mnemonic, reg.getValue(), reg.getValue()));
    } else
      IO.stdout().println(String.format("PC [0x%08x] {= %d}", reg.getValue(), reg.getValue()));
  }

}
