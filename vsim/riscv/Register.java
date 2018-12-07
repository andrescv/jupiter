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

import vsim.Settings;
import vsim.utils.Colorize;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * The Register class represents a "hardware" register.
 */
public final class Register {

  /** register number */
  private final SimpleIntegerProperty number;
  /** register mnemonic */
  private final SimpleStringProperty mnemonic;
  /** register current string value */
  private final SimpleStringProperty strValue;

  /** register current value */
  private int value;
  /** register default value */
  private final int resetValue;
  /** if the register is editable or not */
  private final boolean editable;

  /**
   * Unique constructor that initializes a new register.
   *
   * @param number register number
   * @param mnemonic register mnemonic
   * @param value register initial value
   * @param editable if the register is editable or not
   * @see vsim.riscv.RVIRegisterFile
   * @see vsim.riscv.RVFRegisterFile
   */
  public Register(int number, String mnemonic, int value, boolean editable) {
    this.number = new SimpleIntegerProperty(number);
    this.mnemonic = new SimpleStringProperty(mnemonic);
    this.strValue = new SimpleStringProperty(String.format("0x%08x", value));
    this.value = value;
    this.resetValue = value;
    this.editable = editable;
  }

  /**
   * This method returns de number property.
   *
   * @return number property
   */
  public SimpleIntegerProperty numberProperty() {
    return this.number;
  }

  /**
   * This method returns the mnemonic property.
   *
   * @return mnemonic property
   */
  public SimpleStringProperty mnemonicProperty() {
    return this.mnemonic;
  }

  /**
   * This method returns the string value property.
   *
   * @return string value property
   */
  public SimpleStringProperty strValueProperty() {
    return this.strValue;
  }

  /**
   * This method returns the number of the register.
   *
   * @return the number of the register
   */
  public int getNumber() {
    return this.number.get();
  }

  /**
   * This method returns the mnemonic of the register.
   *
   * @return register mnemonic
   */
  public String getMnemonic() {
    return this.mnemonic.get();
  }

  /**
   * This method returns the string value of the register.
   *
   * @return the string value of the register
   */
  public String getStrValue() {
    return this.strValue.get();
  }

  /**
   * This method returns the value of the register.
   *
   * @return the value of the register
   */
  public int getValue() {
    return this.value;
  }

  /**
   * This method returns the editable property of the register.
   *
   * @return true if the register is editable, false otherwise.
   */
  public boolean isEditable() {
    return this.editable;
  }

  /**
   * This method sets the value of the register if the register is editable.
   *
   * @param value the new register value
   */
  public void setValue(int value) {
    if (this.editable) {
      this.value = value;
      this.update();
    }
  }

  /**
   * This method resets the register to its default value.
   */
  public void reset() {
    this.value = this.resetValue;
    this.update();
  }

  /**
   * This method updates the string value property.
   */
  public void update() {
    // display in hex format
    if (Settings.DISP_REG_HEX.get())
      this.strValue.set(String.format("0x%08x", this.value));
    // display in decimal format
    else
      this.strValue.set(String.format("%d", this.value));
  }

  /**
   * This method returns a String representation of a Register object.
   *
   * @return a pretty string representation
   */
  @Override
  public String toString() {
    return Colorize.blue(String.format("0x%08x", this.value));
  }

}
