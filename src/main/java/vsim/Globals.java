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

package vsim;

import java.util.HashMap;

import vsim.asm.SymbolTable;
import vsim.riscv.InstructionSet;


/** V-Sim global constants and data structures. */
public final class Globals {

  /** V-Sim simulator version */
  public static final String VERSION = "v3.0.0";

  /** V-Sim simulator license and copyright */
  public static final String LICENSE = "Copyright (c) 2018-2019 Andrés Castellanos";

  /** V-Sim simulator online documentation */
  public static final String HELP = "https://andrescv.github.io/V-Sim";

  /** V-Sim RISC-V instruction set */
  public static final InstructionSet iset = new InstructionSet();

  /** Global symbol table */
  public static final SymbolTable globl = new SymbolTable();

  /** Local symbol table */
  public static final HashMap<String, SymbolTable> local = new HashMap<>();

}
