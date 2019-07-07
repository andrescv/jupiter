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

import vsim.utils.IO;


/** Register file for the F extension. */
public final class RVFRegisterFile extends RegisterFile {

  /** register file mnemonics */
  private static final String[] mnemonics = {
    "ft0", "ft1", "ft2", "ft3", "ft4", "ft5", "ft6", "ft7", "fs0", "fs1", "fa0", "fa1", "fa2",
    "fa3", "fa4", "fa5", "fa6", "fa7", "fs2", "fs3", "fs4", "fs5", "fs6", "fs7", "fs8", "fs9",
    "fs10", "fs11", "ft8", "ft9", "ft10", "ft11"
  };

  /** Creates a new RVF register file. */
  public RVFRegisterFile() {
    super(32, "f");
    // add 32 float registers
    for (int i = 0; i < mnemonics.length; i++) {
      // all registers are editable
      Register reg = new Register(i, mnemonics[i], 0, true);
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
   * @throws IllegalArgumentException if the number is invalid
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
   * @throws IllegalArgumentException if the name is invalid
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
   * @throws IllegalArgumentException if the number is invalid
   */
  public void setRegister(int number, float value) {
    setRegister(number, Float.floatToIntBits(value));
  }

  /**
   * Sets the float value of a register given its name.
   *
   * @param name register ABI name
   * @param value register new float value
   * @throws IllegalArgumentException if the name is invalid
   */
  public void setRegister(String name, float value) {
    setRegister(name, Float.floatToIntBits(value));
  }

  /** {@inheritDoc} */
  @Override
  public void print() {
    String fmt = "%s%s (%s)%s[0x%08x] {~= %.6f}";
    // include all registers in out string
    for (int i = 0; i < size; i++) {
      Register reg = rf.get(prefix + i);
      String number = prefix + i;
      String space1 = (i >= 10) ? "" : " ";
      int length = reg.getMnemonic().length();
      String space2 = (length == 4) ? " " : "  ";
      String mnemonic = reg.getMnemonic();
      float value = Float.intBitsToFloat(reg.getValue());
      IO.stdout().println(String.format(fmt, number, space1, mnemonic, space2, reg.getValue(), value));
    }
  }

  /** {@inheritDoc} */
  @Override
  public void print(String name) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("Invalid Register: " + name);
    String fmt = "%s (%s) [0x%08X] {~= %.6f}";
    String number = prefix + reg.getNumber();
    String mnemonic = reg.getMnemonic();
    float value = Float.intBitsToFloat(reg.getValue());
    IO.stdout().println(String.format(fmt, number, mnemonic, reg.getValue(), value));
  }

}
