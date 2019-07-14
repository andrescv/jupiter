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

import java.io.File;

import javafx.beans.property.SimpleStringProperty;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import vsim.utils.Data;


/** Symbol item for symbol table. */
public final class SymbolItem extends RecursiveTreeObject<SymbolItem> implements Comparable<SymbolItem> {

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
  public SymbolItem(File file) {
    name = new SimpleStringProperty(file.toString());
    address = new SimpleStringProperty("");
  }

  /**
   * Creates a new Symbol.
   *
   * @param name symbol name or file name
   * @param address address of the symbol (if isFile = true this is ignored)
   * @param isFile if the instance just represents a filename
   */
  public SymbolItem(String name, int address) {
    this.name = new SimpleStringProperty(name);
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
  }

  /**
   * Returns the name property shown in the Symbol column.
   *
   * @return name property
   */
  public SimpleStringProperty nameProperty() {
    return name;
  }

  /**
   * Returns the address property shown in the Address column.
   *
   * @return address property
   */
  public SimpleStringProperty addressProperty() {
    return address;
  }

  /** {@inheritDoc} */
  @Override
  public int compareTo(SymbolItem other) {
    long a = Integer.toUnsignedLong(Data.atoi(address.get()));
    long b = Integer.toUnsignedLong(Data.atoi(other.address.get()));
    return Long.compare(a, b);
 }

}
