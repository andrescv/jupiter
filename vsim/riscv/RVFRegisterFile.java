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


/**
 * The class RVFRegisterFile represents the F extension register file.
 */
public final class RVFRegisterFile {

  /** RVF register ABI names */
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

  /** the only available instance of the RVFRegisterFile class */
  public static final RVFRegisterFile regfile = new RVFRegisterFile();

  /** register file dictionary */
  private HashMap<String, Register> rf;

  /**
   * Unique constructor that initializes a newly RVFRegisterFile object.
   *
   * @see vsim.riscv.Register
   * @see vsim.riscv.RVIRegisterFile
   */
  private RVFRegisterFile() {
    this.rf = new HashMap<String, Register>();
    // add 32 general purpose registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      // all registers are editable
      Register reg = new RVFRegister(i, MNEMONICS[i], 0, true);
      // abi name
      this.rf.put(MNEMONICS[i], reg);
      // default name f0-f31
      this.rf.put("f" + i, reg);
    }
  }

  /**
   * This method returns the mnemonic of a register
   *
   * @param number register number
   * @return register mnemonic or null if number is invalid
   */
  public String getRegisterMnemonic(int number) {
    Register reg = this.rf.get("f" + number);
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
   * This method returns the float content of a register.
   *
   * @param number register number
   * @return register value or 0 if the number is invalid
   */
  public float getRegister(int number) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      return Float.intBitsToFloat(reg.getValue());
    return 0.0f;
  }

  /**
   * This method returns the float content of a register.
   *
   * @param name register ABI name
   * @return register value or 0 if the name is invalid
   */
  public float getRegister(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return Float.intBitsToFloat(reg.getValue());
    return 0.0f;
  }

  /**
   * This method returns the integer content of a register.
   *
   * @param number register number
   * @return register value or 0 if the name is invalid
   */
  public int getRegisterInt(int number) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  /**
   * This method returns the integer content of a register.
   *
   * @param name register ABI name
   * @return register value or 0 if the name is invalid
   */
  public int getRegisterInt(String name) {
    Register reg = this.rf.get(name);
    if (reg != null)
      return reg.getValue();
    return 0;
  }

  /**
   * This method tries to set the float value of a register.
   *
   * @param number register number
   * @param value register new value
   */
  public void setRegister(int number, float value) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      reg.setValue(Float.floatToIntBits(value));
  }

  /**
   * This method tries to set the float value of a register.
   *
   * @param name register ABI name
   * @param value register new value
   */
  public void setRegister(String name, float value) {
    Register reg = this.rf.get(name);
    if (reg != null)
      reg.setValue(Float.floatToIntBits(value));
  }

  /**
   * This method tries to set the integer value of a register.
   *
   * @param number register number
   * @param value register new value
   */
  public void setRegisterInt(int number, int value) {
    Register reg = this.rf.get("f" + number);
    if (reg != null)
      reg.setValue(value);
  }

  /**
   * This method resets all the registers to their respective default values.
   */
  public void reset() {
    // reset all 32 registers
    for (int i = 0; i < MNEMONICS.length; i++) {
      this.rf.get("f" + i).reset();
    }
  }

  /**
   * This method pretty prints the register file.
   */
  public void print() {
    String regfmt = "%s%s [%s] (%s)%s{~= %.4f}";
    // include all registers in out string
    for (int i = 0; i < MNEMONICS.length; i++) {
      Register reg = this.rf.get("f" + i);
      IO.stdout.println(
        String.format(
          regfmt,
          Colorize.green("f" + i),
          (i >= 10) ? "" : " ",
          reg.toString(),
          Colorize.purple(MNEMONICS[i]),
          (MNEMONICS[i].length() < 4) ? "  " : " ",
          Float.intBitsToFloat(reg.getValue())
        )
      );
    }
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
          "%s [%s] (%s) {~= %.4f}",
          Colorize.green("f" + i),
          reg.toString(),
          Colorize.purple(MNEMONICS[i]),
          Float.intBitsToFloat(reg.getValue())
        )
      );
    }
  }

  /**
   * This method returns an observable list of registers.
   *
   * @return observable list of registers
   */
  public ObservableList<Register> getRVF() {
    ObservableList<Register> rvf = FXCollections.observableArrayList();
    for (int i = 0; i < MNEMONICS.length; i++)
      rvf.add(this.rf.get("f" + i));
    return rvf;
  }

}
