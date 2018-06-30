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

import vsim.utils.IO;
import java.util.Set;
import java.util.HashMap;
import vsim.utils.Colorize;


/**
 * The class SymbolTable is used to represent an assembler symbol table.
 */
public final class SymbolTable {

  /** stores the current symbols */
  private HashMap<String, Symbol> table;

  /**
   * Unique constructor that initializes a newly and empty symbol table.
   */
  public SymbolTable() {
    this.table = new HashMap<String, Symbol>();
  }

  /**
   * This method clears all the symbols of the symbol table.
   */
  public void reset() {
    this.table.clear();
    System.gc();
  }

  /**
   * This method pretty prints the complete symbol table.
   */
  public void print() {
    for (String label: this.table.keySet()) {
      IO.stdout.print(Colorize.green(label) + " ");
      this.table.get(label).print();
    }
  }

  /**
   * This method tries to return the address of a symbol given
   * a label name.
   *
   * @param label the label name
   * @return the address of this symbol or null if the label does not exists
   */
  public Integer get(String label) {
    if (this.table.containsKey(label))
      return this.table.get(label).getAddress();
    return null;
  }

  /**
   * This method tries to return the Sym object given a label name.
   *
   * @param label the label name
   * @see vsim.assembler.Symbol
   * @return the {@code Symbol} object attached to this label, null if the label does not exists
   */
  public Symbol getSymbol(String label) {
    return this.table.get(label);
  }

  /**
   * This method tries to set a new address of a preset label.
   *
   * @param label the label name
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
   * @param label the label name of this symbol
   * @param sym the Symbol object
   * @see vsim.assembler.Symbol
   * @return true if the label does not exists int the symbol table, false otherwise
   */
  public boolean add(String label, Symbol sym) {
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
   * @param label the label name of this symbol
   * @param segment the segment this label belongs
   * @param address the address of this symbol
   * @see vsim.assembler.Segment
   * @see vsim.assembler.Symbol
   * @return true if the label does not exists in the symbol table, false otherwise
   */
  public boolean add(String label, Segment segment, int address) {
    if (!this.table.containsKey(label)) {
      this.add(label, new Symbol(segment, address));
      return true;
    }
    return false;
  }

  /**
   * This method returns all the label names within the symbol table.
   *
   * @return all the label names within the symbol table (if any)
   */
  public Set<String> labels() {
    return this.table.keySet();
  }

}
