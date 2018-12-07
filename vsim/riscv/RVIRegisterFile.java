/*
Copyright (C) 2018 Andres Castellanos

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

package vsim.riscv;

import vsim.utils.IO;
import java.util.HashMap;
import vsim.utils.Colorize;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static vsim.riscv.instructions.Instruction.LENGTH;


/**
 * The class RVIRegisterFile represents the base integer ISA register file.
 */
public final class RVIRegisterFile {

  /** RVI register ABI names */
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

  /** the only available instance of the RVIRegisterFile class */
  public static final RVIRegisterFile regfile = new RVIRegisterFile();

  /** register file dictionary */
  private HashMap<String, Register> rf;
  /** program counter register */
  private Register pc;

  /**
   * Unique constructor that initializes a newly RVIRegisterFile object.
   *
   * @see vsim.riscv.Register
   * @see vsim.riscv.RVFRegisterFile
   */
  private RVIRegisterFile() {
    this.rf = new HashMap<String, Register>();
    // add 32 general purpose registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      // note: only "zero" register is not editable
      Register reg = new RVIRegister(i, MNEMONICS[i], 0, i != 0);
      // point all names to this new register in Hashtable
      String[] names = MNEMONICS[i].split("/");
      for (int j = 0; j < names.length; j++)
        this.rf.put(names[j], reg);
      // default name x[0-9]+
      this.rf.put("x" + i, reg);
    }
    // program counter is a special register
    this.pc = new RVIRegister(32, "pc", MemorySegments.TEXT_SEGMENT_BEGIN, true);
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemorySegments.STACK_POINTER);
    this.rf.get("gp").setValue(MemorySegments.STATIC_SEGMENT);
  }

  /**
   * This method returns the mnemonic of a register
   *
   * @param number register number
   * @return register mnemonic or null if number is invalid
   */
  public String getRegisterMnemonic(int number) {
    Register reg = this.rf.get("x" + number);
    if (reg != null)
      return reg.getMnemonic();
    return null;
  }

  /**
   * This method returns the number of a register given a name.
   *
   * @param name register ABI name
   * @return register number or -1 if the name is invalid
   */
  public int getRegisterNumber(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getNumber();
    return -1;
  }

  /**
   * This method returns the content of a register.
   *
   * @param number register number
   * @return register value or 0 if the number is invalid
   */
  public int getRegister(int number) {
    Register reg = this.rf.get("x" + number);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  /**
   * This method returns the content of a register.
   *
   * @param name register ABI name
   * @return register value or 0 if the name is invalid
   */
  public int getRegister(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  /**
   * This method returns the value of the program counter register.
   *
   * @return the value of the program counter
   */
  public int getProgramCounter() {
    return this.pc.getValue();
  }

  /**
   * This method tries to set the value of a register.
   *
   * @param number register number
   * @param value register new value
   */
  public void setRegister(int number, int value) {
    Register reg = this.rf.get("x" + number);
    if (reg != null)
      reg.setValue(value);
  }

  /**
   * This method tries to set the value of a register.
   *
   * @param name register ABI name
   * @param value register new value
   */
  public void setRegister(String name, int value) {
    Register reg = this.rf.get(name);
    if (reg != null)
      reg.setValue(value);
  }

  /**
   * This method sets the value of the program counter register.
   *
   * @param value program counter new value
   */
  public void setProgramCounter(int value) {
    this.pc.setValue(value);
  }

  /**
   * This method increments the program counter by
   * {@link vsim.riscv.instructions.Instruction#LENGTH}.
   */
  public void incProgramCounter() {
    this.pc.setValue(this.pc.getValue() + LENGTH);
  }

  /**
   * This method resets all the registers to their respective default values.
   */
  public void reset() {
    // reset all 32 registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      this.rf.get("x" + i).reset();
    }
    // use pc default reset value
    this.pc.reset();
    // set default value for stack and global pointer
    this.rf.get("sp").setValue(MemorySegments.STACK_POINTER);
    this.rf.get("gp").setValue(MemorySegments.HEAP_SEGMENT);
  }

  /**
   * This method pretty prints the register file.
   */
  public void print() {
    String regfmt = "%s%s [%s] (%s)%s{= %d}";
    // include all registers in out string
    for (int i = 0; i < MNEMONICS.length; i++) {
      Register reg = this.rf.get("x" + i);
      IO.stdout.println(
        String.format(
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
        )
      );
    }
    // and pc
    IO.stdout.println(
      System.getProperty("line.separator") + String.format(
        "PC  [%s]",
        this.pc.toString()
      )
    );
  }

  /**
   * This method pretty prints a register.
   *
   * @param name register ABI name
   */
  public void printReg(String name) {
    Register reg = this.rf.get(name);
    if (reg != null) {
      int i = reg.getNumber();
      IO.stdout.println(
        String.format(
          "%s [%s] (%s) {= %d}",
          Colorize.green("x" + i),
          reg.toString(),
          Colorize.purple(MNEMONICS[i]),
          reg.getValue()
        )
      );
    } else if (name.equals("pc"))
      IO.stdout.println(
        String.format(
          "PC  [%s]",
          this.pc.toString()
        )
      );
  }

  /**
   * This method returns an observable list of registers.
   *
   * @return observable list of registers
   */
  public ObservableList<Register> getRVI() {
    ObservableList<Register> rvi = FXCollections.observableArrayList();
    for (int i = 0; i < MNEMONICS.length; i++)
      rvi.add(this.rf.get("x" + i));
    return rvi;
  }

}
