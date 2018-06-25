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

import vsim.utils.Colorize;


/**
 * The Register class represents a "hardware" register.
 */
final class Register {

  /** register number */
  private int number;
  /** register current value */
  private int value;
  /** register default value */
  private int resetValue;
  /** if the register is editable or not */
  private boolean editable;

  /**
   * Unique constructor that initializes a newly Register object.
   *
   * @param number register number
   * @param value register initial value
   * @param editable if the register is editable or not
   * @see vsim.riscv.RVIRegisterFile
   * @see vsim.riscv.RVFRegisterFile
   */
  protected Register(int number, int value, boolean editable) {
    this.number = number;
    this.value = value;
    this.resetValue = value;
    this.editable = editable;
  }

  /**
   * This method returns the number of the register.
   */
  protected int getNumber() {
    return this.number;
  }

  /**
   * This method returns the value of the register.
   */
  protected int getValue() {
    return this.value;
  }

  /**
   * This method sets the value of the register if the register is editable.
   *
   * @param value the new register value
   */
  protected void setValue(int value) {
    if (this.editable)
      this.value = value;
  }

  /**
   * This method resets the register to its default value.
   */
  protected void reset() {
    this.value = this.resetValue;
  }

  /**
   * This method returns a String representation of a Register object.
   *
   * @return the String representation
   */
  @Override
  public String toString() {
    return Colorize.blue(String.format("0x%08x", this.value));
  }

}
