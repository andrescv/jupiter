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

package vsim.gui.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.beans.property.SimpleStringProperty;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


/** Register item for register file tables. */
public final class RegisterItem extends RecursiveTreeObject<RegisterItem> implements PropertyChangeListener {

  /** hex display mode */
  public static final int HEX = 0;
  /** decimal display mode */
  public static final int DEC = 1;
  /** unsigned display mode */
  public static final int UNS = 2;
  /** float display mode */
  public static final int FLT = 3;

  /** display mode */
  private int mode;
  /** register int value */
  private int intValue;

  /** register mnemonic */
  private final SimpleStringProperty mnemonic;
  /** register number */
  private final SimpleStringProperty number;
  /** register current string value */
  private final SimpleStringProperty value;

  /**
   * Creates a new register item.
   *
   * @param mnemonic register mnemonic
   * @param number register number
   * @param value intial register value
   */
  public RegisterItem(String mnemonic, String number, int value) {
    mode = HEX;
    intValue = value;
    this.mnemonic = new SimpleStringProperty(mnemonic);
    this.number = new SimpleStringProperty(number);
    this.value = new SimpleStringProperty(getValue(value));
  }

  /**
   * Returns the mnemonic property shown in the Mnemonic column.
   *
   * @return register mnemonic property
   */
  public SimpleStringProperty mnemonicProperty() {
    return mnemonic;
  }

  /**
   * Returns the number property shown in the Number column.
   *
   * @return register number property
   */
  public SimpleStringProperty numberProperty() {
    return number;
  }

  /**
   * Returns the value property shown in the Value column.
   *
   * @return register value property
   */
  public SimpleStringProperty valueProperty() {
    return value;
  }

  /**
   * Returns the value shown in the Value column.
   *
   * @return register value
   */
  public String getValue() {
    return value.get();
  }

  /**
   * Updates display mode.
   *
   * @param mode new display mode
   */
  public void display(int mode) {
    this.mode = mode;
    value.set(getValue(intValue));
  }

  /** {@inheritDoc} */
  @Override
  public void propertyChange(PropertyChangeEvent e) {
    if (e.getPropertyName().equals(mnemonic.get()) && !mnemonic.get().equals("zero")) {
      intValue = (int) e.getNewValue();
      value.set(getValue(intValue));
    }
  }

  /**
   * Returns the String representation of the register value.
   *
   * @param data value
   * @return String representation of the register value
   */
  private String getValue(int data) {
    switch (mode) {
      case DEC:
        return String.format("%d", data);
      case UNS:
        return String.format("%d", Integer.toUnsignedLong(data));
      case FLT:
        return String.format("%f", Float.intBitsToFloat(data));
      default:
        return String.format("0x%08x", data);
    }
  }

}
