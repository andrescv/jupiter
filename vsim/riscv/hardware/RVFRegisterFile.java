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

import vsim.utils.Colorize;
import vsim.utils.IO;


/**
 * The class RVFRegisterFile represents the register file of the F extension.
 */
public final class RVFRegisterFile extends RegisterFile {

  /** unique instance of the RVF register file */
  public static final RVFRegisterFile regfile = new RVFRegisterFile();

  /**
   * Unique constructor that initializes a newly RVFRegisterFile object.
   *
   * @see vsim.riscv.Register
   * @see vsim.riscv.RVIRegisterFile
   */
  private RVFRegisterFile() {
    super(32, "f");
    String[] mnemonics = { "ft0", "ft1", "ft2", "ft3", "ft4", "ft5", "ft6", "ft7", "fs0", "fs1", "fa0", "fa1", "fa2",
        "fa3", "fa4", "fa5", "fa6", "fa7", "fs2", "fs3", "fs4", "fs5", "fs6", "fs7", "fs8", "fs9", "fs10", "fs11",
        "ft8", "ft9", "ft10", "ft11" };
    // add 32 general purpose registers
    for (int i = 0; i < mnemonics.length; i++) {
      // all registers are editable
      Register reg = new RVFRegister(i, mnemonics[i], 0, true);
      // abi name
      rf.put(mnemonics[i], reg);
      // default name f0-f31
      rf.put("f" + i, reg);
    }
  }

  /**
   * Gets the float content of a register given its number.
   *
   * @param number register number
   * @return register float value
   */
  public float getRegisterFloat(int number) {
    Register reg = rf.get(prefix + number);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + (prefix + number));
    return Float.intBitsToFloat(reg.getValue());
  }

  /**
   * Gets the float content of a register given its name.
   *
   * @param name register ABI name
   * @return register float value
   */
  public float getRegisterFloat(String name) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    return Float.intBitsToFloat(reg.getValue());
  }

  /**
   * Sets the float value of a register given its number.
   *
   * @param number register number
   * @param value register new float value
   */
  public void setRegister(int number, float value) {
    Register reg = rf.get(prefix + number);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + (prefix + number));
    int intVal = Float.floatToIntBits(value);
    if (intVal != reg.getValue())
      diff.put(prefix + number, reg.getValue());
    reg.setValue(intVal);
  }

  /**
   * Sets the float value of a register given its name.
   *
   * @param name register ABI name
   * @param value register new float value
   */
  public void setRegister(String name, float value) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    int intVal = Float.floatToIntBits(value);
    if (intVal != reg.getValue())
      diff.put(prefix + reg.getNumber(), reg.getValue());
    reg.setValue(intVal);
  }

  /**
   * This method pretty prints the register file.
   */
  public void print() {
    String fmt = "%s%s (%s)%s[%s] {~= %.6f}";
    // include all registers in out string
    for (int i = 0; i < size; i++) {
      Register reg = rf.get(prefix + i);
      String number = Colorize.green(prefix + i);
      String space1 = (i >= 10) ? "" : " ";
      int length = reg.getMnemonic().length();
      String space2 = (length == 4) ? " " : ((length == 3) ? "  " : "   ");
      String mnemonic = Colorize.purple(reg.getMnemonic());
      float value = Float.intBitsToFloat(reg.getValue());
      IO.stdout.println(String.format(fmt, number, space1, mnemonic, space2, reg.toString(), value));
    }
  }

  /**
   * This method pretty prints a register.
   *
   * @param name register ABI name
   */
  public void printReg(String name) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    String fmt = "%s (%s) [%s] {~= %.6f}";
    String number = Colorize.green(prefix + reg.getNumber());
    String mnemonic = Colorize.purple(reg.getMnemonic());
    float value = Float.intBitsToFloat(reg.getValue());
    IO.stdout.println(String.format(fmt, number, mnemonic, reg.toString(), value));
  }

}
