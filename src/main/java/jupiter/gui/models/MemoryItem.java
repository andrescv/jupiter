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

package jupiter.gui.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.beans.property.SimpleStringProperty;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import jupiter.utils.Data;


/** Register item for register file tables. */
public final class MemoryItem extends RecursiveTreeObject<MemoryItem> implements PropertyChangeListener {

  /** hex display mode */
  public static final int HEX = 0;
  /** decimal display mode */
  public static final int DEC = 1;
  /** ascii display mode */
  public static final int ASC = 2;

  /** memory int address */
  private int intAddress;
  /** memory cell byte 0 */
  private int intByte0;
  /** memory cell byte 1 */
  private int intByte1;
  /** memory cell byte 2 */
  private int intByte2;
  /** memory cell byte 3 */
  private int intByte3;

  /** display mode */
  private int mode;

  /** if memory cell has changed */
  private boolean updated;

  /** memory item address */
  private final SimpleStringProperty address;
  /** memory item byte 0 */
  private final SimpleStringProperty byte0;
  /** memory item byte 1 */
  private final SimpleStringProperty byte1;
  /** memory item byte 2 */
  private final SimpleStringProperty byte2;
  /** memory item byte 3 */
  private final SimpleStringProperty byte3;

  /**
   * Creates a new memory item.
   *
   * @param address memory address
   * @param byte0 memory byte at address
   * @param byte1 memory byte at address + 1
   * @param byte2 memory byte at address + 2
   * @param byte3 memory byte at address + 3
   */
  public MemoryItem(int address, int byte0, int byte1, int byte2, int byte3) {
    mode = HEX;
    intAddress = address;
    intByte0 = byte0;
    intByte1 = byte1;
    intByte2 = byte2;
    intByte3 = byte3;
    updated = false;
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
    this.byte0 = new SimpleStringProperty(String.format("%02x", byte0));
    this.byte1 = new SimpleStringProperty(String.format("%02x", byte1));
    this.byte2 = new SimpleStringProperty(String.format("%02x", byte2));
    this.byte3 = new SimpleStringProperty(String.format("%02x", byte3));
  }

  /**
   * Updates memory cell values.
   *
   * @param address memory address
   * @param byte0 memory byte at address
   * @param byte1 memory byte at address + 1
   * @param byte2 memory byte at address + 2
   * @param byte3 memory byte at address + 3
   */
  public void update(int address, int byte0, int byte1, int byte2, int byte3) {
    intAddress = address;
    intByte0 = byte0;
    intByte1 = byte1;
    intByte2 = byte2;
    intByte3 = byte3;
    this.address.set(String.format("0x%08x", address));
    this.byte0.set(getByte(byte0));
    this.byte1.set(getByte(byte1));
    this.byte2.set(getByte(byte2));
    this.byte3.set(getByte(byte3));
    updated = false;
  }

  /**
   * Updates display mode.
   *
   * @param mode display mode
   */
  public void display(int mode) {
    this.mode = mode;
    byte0.set(getByte(intByte0));
    byte1.set(getByte(intByte1));
    byte2.set(getByte(intByte2));
    byte3.set(getByte(intByte3));
  }

  /**
   * Returns item int address.
   *
   * @return memory int address
   */
  public int getIntAddress() {
    return intAddress;
  }

  /**
   * Returns the address shown in the Address column.
   *
   * @return address property
   */
  public SimpleStringProperty addressProperty() {
    return address;
  }

  /**
   * Returns the byte property shown in the +0 column.
   *
   * @return byte0 property
   */
  public SimpleStringProperty byte0Property() {
    return byte0;
  }

  /**
   * Returns the byte property shown in the +1 column.
   *
   * @return byte1 property
   */
  public SimpleStringProperty byte1Property() {
    return byte1;
  }

  /**
   * Returns the byte property shown in the +2 column.
   *
   * @return byte2 property
   */
  public SimpleStringProperty byte2Property() {
    return byte2;
  }

  /**
   * Returns the byte property shown in the +3 column.
   *
   * @return byte3 property
   */
  public SimpleStringProperty byte3Property() {
    return byte3;
  }

  /**
   * Returns the byte shown in the +0 column.
   *
   * @return byte0
   */
  public String getByte0() {
    return byte0.get();
  }

  /**
   * Returns the byte shown in the +1 column.
   *
   * @return byte1
   */
  public String getByte1() {
    return byte1.get();
  }

  /**
   * Returns the byte shown in the +2 column.
   *
   * @return byte2
   */
  public String getByte2() {
    return byte2.get();
  }

  /**
   * Returns the byte shown in the +3 column.
   *
   * @return byte3
   */
  public String getByte3() {
    return byte3.get();
  }

  /**
   * Verifies if the memory cell has been updated.
   *
   * @return {@code true} if memory cell has been updated, {@code false} if not
   */
  public boolean updated() {
    return updated;
  }

  /** {@inheritDoc} */
  @Override
  public void propertyChange(PropertyChangeEvent e) {
    String op = (String) e.getOldValue();
    int addr = Data.atoi(e.getPropertyName());
    if (op.equals("store")) {
      if (addr == intAddress) {
        updated = true;
        intByte0 = (int) e.getNewValue();
        byte0.set(getByte(intByte0));
      } else if (addr == (intAddress + 1)) {
        updated = true;
        intByte1 = (int) e.getNewValue();
        byte1.set(getByte(intByte1));
      } else if (addr == (intAddress + 2)) {
        updated = true;
        intByte2 = (int) e.getNewValue();
        byte2.set(getByte(intByte2));
      } else if (addr == (intAddress + 3)) {
        updated = true;
        intByte3 = (int) e.getNewValue();
      } else {
        updated = false;
      }
    }
  }

  /**
   * Returns the String representation of the byte.
   *
   * @param data byte to get
   * @return String representation of the byte
   */
  private String getByte(int data) {
    switch (mode) {
      case DEC:
        return String.format("%d", (byte) data);
      case ASC:
        return getChar(data);
      default:
        return String.format("%02x", (byte) data);
    }
  }

  /**
   * Gets ascii representation of a memory byte at address given.
   *
   * @param address memory address
   * @return ascii representation of the memory byte
   */
  private String getChar(int ch) {
    char c = (char) ch;
    if (c >= 32 && c <= 126) {
      return "'" + c + "'";
    } else {
      switch (c) {
        case 0:
          return "'\\0'";
        case 8:
          return "'\\b'";
        case 9:
          return "'\\t'";
        case 10:
          return "'\\n'";
        case 11:
          return "'\\v'";
        case 12:
          return "'\\f'";
        case 13:
          return "'\\r'";
        default:
          return "�";
      }
    }
  }

}
