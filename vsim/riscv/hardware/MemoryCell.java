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

import javafx.beans.property.SimpleStringProperty;
import vsim.Settings;


/**
 * This class represents a memory cell. Useful only in the GUI application.
 */
public final class MemoryCell {

  /** integer address of the cell */
  private int intAddress;
  /** memory cell address property */
  private final SimpleStringProperty address;
  /** memory cell offset 0 property */
  private final SimpleStringProperty offset0;
  /** memory cell offset 1 property */
  private final SimpleStringProperty offset1;
  /** memory cell offset 2 property */
  private final SimpleStringProperty offset2;
  /** memory cell offset 3 property */
  private final SimpleStringProperty offset3;

  /**
   * Creates a new memory cell.
   *
   * @param address address of the memory cell
   */
  public MemoryCell(int address) {
    this.intAddress = address;
    // default values
    this.address = new SimpleStringProperty(String.format("0x%08x", this.intAddress));
    this.offset0 = new SimpleStringProperty(String.format("%02x", 0));
    this.offset1 = new SimpleStringProperty(String.format("%02x", 0));
    this.offset2 = new SimpleStringProperty(String.format("%02x", 0));
    this.offset3 = new SimpleStringProperty(String.format("%02x", 0));
  }

  /**
   * This method returns the address property of the memory cell.
   *
   * @return address property of memory cell
   */
  public SimpleStringProperty addressProperty() {
    return this.address;
  }

  /**
   * This method returns the offset0 property of the memory cell.
   *
   * @return offset0 property of memory cell.
   */
  public SimpleStringProperty offset0Property() {
    return this.offset0;
  }

  /**
   * This method returns the offset1 property of the memory cell.
   *
   * @return offset1 property of memory cell.
   */
  public SimpleStringProperty offset1Property() {
    return this.offset1;
  }

  /**
   * This method returns the offset2 property of the memory cell.
   *
   * @return offset2 property of memory cell.
   */
  public SimpleStringProperty offset2Property() {
    return this.offset2;
  }

  /**
   * This method returns the offset3 property of the memory cell.
   *
   * @return offset3 property of memory cell.
   */
  public SimpleStringProperty offset3Property() {
    return this.offset3;
  }

  /**
   * This methods returns the current address of the memory cell.
   *
   * @return memory cell address
   */
  public String getAddress() {
    return this.address.get();
  }

  /**
   * This method returns the first byte at memory cell address.
   *
   * @return first byte at memory cell address.
   */
  public String getOffset0() {
    return this.offset0.get();
  }

  /**
   * This method returns the second byte at memory cell address.
   *
   * @return second byte at memory cell address.
   */
  public String getOffset1() {
    return this.offset1.get();
  }

  /**
   * This method returns the third byte at memory cell address.
   *
   * @return third byte at memory cell address.
   */
  public String getOffset2() {
    return this.offset2.get();
  }

  /**
   * This method returns the fourth byte at memory cell address.
   *
   * @return fourth byte at memory cell address.
   */
  public String getOffset3() {
    return this.offset3.get();
  }

  /**
   * This method returns the integer memory cell address.
   *
   * @return integer memory cell address
   */
  public int getIntAddress() {
    return this.intAddress;
  }

  /**
   * Sets the integer memory cell address and updates all internal properties.
   *
   * @param address memory cell address
   */
  public void setIntAddress(int address) {
    this.intAddress = address;
    this.update();
  }

  /**
   * Updates all internal properties with the help of the Main memory.
   */
  public void update() {
    this.address.set(String.format("0x%08x", this.intAddress));
    // hex mode
    if (Settings.DISP_MEM_CELL == 0) {
      this.offset0.set(String.format("%02x", Memory.ram.privLoadByteUnsigned(this.intAddress)));
      this.offset1.set(String.format("%02x", Memory.ram.privLoadByteUnsigned(this.intAddress + 1)));
      this.offset2.set(String.format("%02x", Memory.ram.privLoadByteUnsigned(this.intAddress + 2)));
      this.offset3.set(String.format("%02x", Memory.ram.privLoadByteUnsigned(this.intAddress + 3)));
    }
    // ascii mode
    else if (Settings.DISP_MEM_CELL == 1) {
      this.offset0.set(this.getChar(this.intAddress));
      this.offset1.set(this.getChar(this.intAddress + 1));
      this.offset2.set(this.getChar(this.intAddress + 2));
      this.offset3.set(this.getChar(this.intAddress + 3));
    }
    // decimal mode
    else {
      this.offset0.set(String.format("%d", Memory.ram.privLoadByteUnsigned(this.intAddress)));
      this.offset1.set(String.format("%d", Memory.ram.privLoadByteUnsigned(this.intAddress + 1)));
      this.offset2.set(String.format("%d", Memory.ram.privLoadByteUnsigned(this.intAddress + 2)));
      this.offset3.set(String.format("%d", Memory.ram.privLoadByteUnsigned(this.intAddress + 3)));
    }
  }

  /**
   * Gets ascii representation of a memory byte at address given.
   *
   * @param address memory address
   * @return ascii representation of the memory byte
   */
  private String getChar(int address) {
    char c = (char) Memory.ram.privLoadByteUnsigned(address);
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
