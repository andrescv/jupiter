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

import java.util.HashMap;


/** Represents a basic and general register file. */
public abstract class RegisterFile {

  /** register file size */
  protected final int size;
  /** register raw prefix e.g x or f */
  protected final String prefix;
  /** register lookup */
  protected final HashMap<String, Register> rf;
  /** current register file diff */
  protected HashMap<String, Integer> diff;

  /**
   * Creates a general register file.
   *
   * @param prefix registers prefix e.g x or f
   */
  protected RegisterFile(int size, String prefix) {
    this.size = size;
    this.prefix = prefix;
    rf = new HashMap<>();
    diff = new HashMap<>();
  }

  /** Pretty prints the register file . */
  public abstract void print();

  /**
   * Pretty prints a register.
   *
   * @param name register ABI name
   * @throws IllegalArgumentException if the name is invalid
   */
  public abstract void print(String name);

  /** Resets register file state. */
  public void reset() {
    // clear all registers
    for (int i = 0; i < size; i++) {
      rf.get(prefix + i).reset();
    }
  }

  /**
   * Restores register file state.
   *
   * @param values register values
   */
  public void restore(HashMap<String, Integer> values) {
    for (String key : values.keySet()) {
      rf.get(key).setValue(values.get(key));
    }
  }

  /**
   * Sets the value of a register given its number.
   *
   * @param number register number
   * @param value register new value
   * @throws IllegalArgumentException if the number is invalid
   */
  public void setRegister(int number, int value) {
    Register reg = rf.get(prefix + number);
    if (reg == null)
      throw new IllegalArgumentException("invalid register: " + (prefix + number));
    // save previous register value in diff
    if (value != reg.getValue())
      diff.put(prefix + number, reg.getValue());
    reg.setValue(value);
  }

  /**
   * Sets the value of a register given its name.
   *
   * @param name register ABI name
   * @param value register new value
   * @throws IllegalArgumentException if the name is invalid
   */
  public void setRegister(String name, int value) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("invalid register: " + name);
    // save previous register value in diff
    if (value != reg.getValue())
      diff.put(prefix + reg.getNumber(), reg.getValue());
    reg.setValue(value);
  }

  /**
   * Gets the current diff of the register file.
   *
   * @return register file diff
   */
  public HashMap<String, Integer> getDiff() {
    HashMap<String, Integer> old = diff;
    diff = new HashMap<String, Integer>();
    return old;
  }

  /**
   * Gets the content of a register given its number.
   *
   * @param number register number
   * @return register value
   * @throws IllegalArgumentException if the number is invalid
   */
  public int getRegister(int number) {
    Register reg = rf.get(prefix + number);
    if (reg == null)
      throw new IllegalArgumentException("invalid register: " + (prefix + number));
    return reg.getValue();
  }

  /**
   * Gets the content of a register given its name.
   *
   * @param name register ABI name
   * @return register value or 0 if the name is invalid
   * @throws IllegalArgumentException if the name is invalid
   */
  public int getRegister(String name) {
    Register reg = rf.get(name);
    if (reg == null)
      throw new IllegalArgumentException("invalid register: " + name);
    return reg.getValue();
  }

}
