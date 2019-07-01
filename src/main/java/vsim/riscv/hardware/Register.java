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


/** Represents a "hardware" register. */
public final class Register {

  /** register value */
  private int value;
  /** register number */
  private final int number;
  /** register mnemonic */
  private final String mnemonic;
  /** register default value */
  private final int resetValue;
  /** if the register is editable or not */
  private final boolean editable;

  /**
   * Creates a new 32-bit register.
   *
   * @param number register number
   * @param mnemonic register mnemonic
   * @param value register initial value
   * @param editable if the register is editable or not
   */
  public Register(int number, String mnemonic, int value, boolean editable) {
    this.number = number;
    this.mnemonic = mnemonic;
    this.value = value;
    this.editable = editable;
    resetValue = value;
  }

  /**
   * Returns the register number.
   *
   * @return register number.
   */
  public int getNumber() {
    return number;
  }

  /**
   * Returns the register mnemonic.
   *
   * @return register mnemonic
   */
  public String getMnemonic() {
    return mnemonic;
  }


  /**
   * Returns the register value.
   *
   * @return register value
   */
  public int getValue() {
    return value;
  }

  /**
   * Returns if the register is editable or not.
   *
   * @return true if the register is editable, false otherwise.
   */
  public boolean isEditable() {
    return editable;
  }

  /**
   * Sets the value of the register if the register is editable.
   *
   * @param newValue new register value
   */
  public synchronized void setValue(int newValue) {
    if (this.editable) {
      value = newValue;
    }
  }

  /** Resets register state to its default value. */
  public void reset() {
    value = resetValue;
  }

}
