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

package vsim.riscv.hardware;

import javafx.beans.property.SimpleIntegerProperty;
import vsim.riscv.MemorySegments;
import vsim.utils.Colorize;
import vsim.utils.Data;
import vsim.utils.IO;


/**
 * The class RVIRegisterFile represents the register file of the base integer ISA.
 */
public final class RVIRegisterFile extends RegisterFile {

  /** unique instance of RVI register file */
  public static final RVIRegisterFile regfile = new RVIRegisterFile();

  /** program counter special register */
  private final Register pc;

  /**
   * Creates a RVI register file. This constructor is private because there should be only one instance of this class.
   *
   * @see vsim.hardware.RVIRegisterFile.rvi;
   */
  private RVIRegisterFile() {
    super(32, "x");
    // all rvi register mnemonics
    String[] mnemonics = { "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2", "s0", "s1", "a0", "a1", "a2", "a3", "a4",
        "a5", "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10", "s11", "t3", "t4", "t5", "t6" };
    // add 32 general purpose registers
    for (int i = 0; i < mnemonics.length; i++) {
      // default reset value
      int resetValue = 0;
      // default reset value for stack pointer
      if (mnemonics[i].equals("sp"))
        resetValue = MemorySegments.STACK_POINTER;
      // default reset value for global pointer
      if (mnemonics[i].equals("gp"))
        resetValue = MemorySegments.GLOBAL_POINTER;
      // note: only "zero" register is not editable
      Register reg = new RVIRegister(i, mnemonics[i], resetValue, i != 0);
      // point all names to this new register
      rf.put(mnemonics[i], reg);
      rf.put(prefix + i, reg);
      // special frame pointer
      if (mnemonics[i].equals("s0"))
        rf.put("fp", reg);
    }
    // create program counter
    pc = new RVIRegister(-1, "pc", MemorySegments.TEXT_SEGMENT_BEGIN, true);
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
   * Gets the integer value property of the program counter.
   *
   * @return integer value property of PC
   */
  public SimpleIntegerProperty programCounterProperty() {
    return pc.valueProperty();
  }

  /**
   * Sets the value of the program counter register.
   *
   * @param value program counter new value
   */
  public void setProgramCounter(int value) {
    this.pc.setValue(value);
  }

  /**
   * This method increments the program counter by {@link vsim.utils.Data#WORD_LENGTH}.
   */
  public void incProgramCounter() {
    this.pc.setValue(this.pc.getValue() + Data.WORD_LENGTH);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void print() {
    String fmt = "%s%s (%s)%s[%s] {= %d}";
    // include all registers in out string
    for (int i = 0; i < size; i++) {
      Register reg = rf.get(prefix + i);
      String number = Colorize.green(prefix + i);
      String space1 = (i >= 10) ? "" : " ";
      int length = reg.getMnemonic().length();
      String space2 = (length == 4) ? " " : ((length == 3) ? "  " : "   ");
      String mnemonic = Colorize.purple(reg.getMnemonic());
      IO.stdout.println(String.format(fmt, number, space1, mnemonic, space2, reg.toString(), reg.getValue()));
    }
    // and pc
    IO.stdout.println();
    IO.stdout.println(String.format("PC         [%s] {= %d}", pc.toString(), pc.getValue()));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void printReg(String name) {
    Register reg = null;
    if (name.equals("pc"))
      reg = pc;
    else
      reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    // print regular register
    if (reg != pc) {
      String fmt = "%s (%s) [%s] {= %d}";
      String number = Colorize.green(prefix + reg.getNumber());
      String mnemonic = Colorize.purple(reg.getMnemonic());
      IO.stdout.println(String.format(fmt, number, mnemonic, reg.toString(), reg.getValue()));
    } else
      IO.stdout.println(String.format("PC [%s]", reg.toString()));
  }

}
