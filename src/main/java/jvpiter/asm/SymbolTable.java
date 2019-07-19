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

package jvpiter.asm;

import java.util.HashMap;
import java.util.Set;


/** Assembler symbol table. */
public final class SymbolTable {

  /** lookup table */
  private final HashMap<String, Symbol> table;

  /** Creates a new and empty symbol table. */
  public SymbolTable() {
    table = new HashMap<>();
  }

  /** Resets the symbol table clearing its content. */
  public void reset() {
    table.clear();
  }

  /**
   * Adds or replaces a symbol in the symbol table.
   *
   * @param sym symbol name
   * @param data symbol data
   */
  public void add(String sym, Symbol data) {
    table.put(sym, data);
  }

  /**
   * Adds or replaces a symbol in the symbol table.
   *
   * @param sym symbol name
   * @param segment symbol memory segment
   * @param address symbol address
   */
  public void add(String sym, Segment segment, int address) {
    add(sym, new Symbol(segment, address));
  }

  /**
   * Returns symbol data given the symbol name.
   *
   * @param sym symbol name.
   * @return symbol data
   */
  public Symbol getSymbol(String sym) {
    return table.get(sym);
  }

  /**
   * Verifies if a symbol exists in the symbol table.
   *
   * @param sym symbol name
   * @return {@code true} if the symbol exists in the symbol table, {@code false} if not
   */
  public boolean contains(String sym) {
    return table.containsKey(sym);
  }

  /**
   * Returns a set of symbol names that are in the symbol table.
   *
   * @return set of symbol names
   */
  public Set<String> labels() {
    return table.keySet();
  }

}
