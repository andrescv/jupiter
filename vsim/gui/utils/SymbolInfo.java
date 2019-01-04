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

package vsim.gui.utils;

import javafx.beans.property.SimpleStringProperty;


/**
 * This utility class is used to show assembler symbols in a table view.
 */
public final class SymbolInfo implements Comparable<SymbolInfo> {

  /** if this instance is just a file name and not a symbol */
  private final boolean isFile;
  /** symbol name or file name */
  private final SimpleStringProperty name;
  /** symbol address if the instance is symbol and not just a file name */
  private final SimpleStringProperty address;

  /**
   * Creates a new Symbol.
   *
   * @param name symbol name or file name
   * @param address address of the symbol (if isFile = true this is ignored)
   * @param isFile if the instance just represents a filename
   */
  public SymbolInfo(String name, int address, boolean isFile) {
    this.isFile = isFile;
    this.name = new SimpleStringProperty(name);
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
  }

  /**
   * Gets the symbol or file name.
   *
   * @return symbol or file name
   */
  public String getName() {
    return this.name.get();
  }

  /**
   * Gets the symbol address.
   *
   * @return symbol address
   */
  public String getAddress() {
    return this.address.get();
  }

  /**
   * If the instance is just a file name.
   *
   * @return true if the instance represents a file name, false otherwise
   */
  public boolean isFile() {
    return this.isFile;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int compareTo(SymbolInfo other) {
    long a = Integer.toUnsignedLong(Integer.parseInt(this.getAddress().substring(2), 16));
    long b = Integer.toUnsignedLong(Integer.parseInt(other.getAddress().substring(2), 16));
    return Long.compare(a, b);
  }

}
