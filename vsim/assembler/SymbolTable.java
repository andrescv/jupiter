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

package vsim.assembler;

import vsim.utils.Colorize;
import java.util.Hashtable;
import java.util.Enumeration;


/**
 * The class SymbolTable is used to represent an assembler symbol table.
 */
public final class SymbolTable {

  /** the symbol table */
  private Hashtable<String, Sym> table;

  /**
   * Unique constructor that initializes a newly and empty SymbolTable object.
   *
   * @see vsim.assembler.Sym
   */
  public SymbolTable() {
    this.table = new Hashtable<String, Sym>();
  }

  /**
   * This method clears all the content of the symbol table.
   */
  public void reset() {
    this.table.clear();
    System.gc();
  }

  /**
   * This method pretty prints the complete symbol table.
   */
  public void print() {
    System.out.println(this);
  }

  /**
   * This method tries to return the address of a symbol attached
   * to a label.
   *
   * @param label the label of this symbol
   * @return the address of this symbol or null if the label does not exists
   */
  public Integer get(String label) {
    if (this.table.containsKey(label))
      return this.table.get(label).getAddress();
    return null;
  }

  /**
   * This method tries to return the Sym object attached to a label.
   *
   * @param label the label of this symbol
   * @return the Sym object attached to this label, null if the label does not exists
   */
  public Sym getSymbol(String label) {
    return this.table.get(label);
  }

  /**
   * This method tries to set a new address of a preset label.
   *
   * @param label the label
   * @param address the new address of this label
   * @return true if the label exists in the symbol table, false if not
   */
  public boolean set(String label, int address) {
    if (this.table.containsKey(label)) {
      this.table.get(label).setAddress(address);
      return true;
    }
    return false;
  }

  /**
   * This method tries to add a new Sym object to the symbol table.
   *
   * @param label the label of this symbol
   * @param sym the Sym object
   * @see vsim.assembler.Sym
   * @return true if the label does not exists int the symbol table, false otherwise
   */
  public boolean add(String label, Sym sym) {
    if (!this.table.containsKey(label)) {
      this.table.put(label, sym);
      return true;
    }
    return false;
  }

  /**
   * This method tries to add a new label of a segment to the symbol table
   * creating a newly Sym object.
   *
   * @param label the label of this symbol
   * @param segment the segment this symbol belongs
   * @param address the address of this symbol
   * @see vsim.assembler.Segment
   * @see vsim.assembler.Sym
   * @return true if the label does not exists in the symbol table, false otherwise
   */
  public boolean add(String label, Segment segment, int address) {
    if (!this.table.containsKey(label)) {
      this.add(label, new Sym(segment, address));
      return true;
    }
    return false;
  }

  /**
   * This method returns all the labels within the symbol table.
   *
   * @return all the labels within the symbol table (if any)
   */
  public Enumeration<String> labels() {
    return this.table.keys();
  }

  /**
   * This method returns a String representation of a SymbolTable object.
   *
   * @return the String representation
   */
  @Override
  public String toString() {
    String out = "";
    String newline = System.getProperty("line.separator");
    for (Enumeration<String> e = this.table.keys(); e.hasMoreElements();) {
      String label = e.nextElement();
      out += Colorize.green(label) + " " + this.table.get(label).toString();
      out += newline;
    }
    return out.replaceAll("\\s+$", "");
  }

}
