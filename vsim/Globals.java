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

package vsim;

import vsim.riscv.Memory;
import java.util.Hashtable;
import java.util.ArrayList;
import vsim.riscv.InstructionSet;
import vsim.riscv.RVIRegisterFile;
import vsim.riscv.RVFRegisterFile;
import vsim.assembler.SymbolTable;


/**
 * The Globals class contains a collection of globally-available data structures.
 */
public final class Globals {

  /** RISC-V (RV32) principal memory (RAM) */
  public static final Memory memory = Memory.ram;

  /** RV32I register file */
  public static final RVIRegisterFile regfile = RVIRegisterFile.regfile;

  /** RV32F register file */
  public static final RVFRegisterFile fregfile = RVFRegisterFile.regfile;

  /** RISC-V (RV32IMF) instruction set */
  public static final InstructionSet iset = InstructionSet.insts;

  /** .globl symbol table (used for debugging and global symbols) */
  public static final SymbolTable globl = new SymbolTable();

  /** local symbol table per file (used for debugging and local symbols) */
  public static Hashtable<String, SymbolTable> local = new Hashtable<String, SymbolTable>();

  /**
   * This method resets the simulator state
   */
  public static void resetState() {
    // reset memory
    Globals.memory.reset();
    // reset registers
    Globals.regfile.reset();
    // reset floating point registers
    Globals.fregfile.reset();
  }

  /**
   * This method resets the local and global symbol tables
   */
  public static void resetST() {
    Globals.globl.reset();
    Globals.local = new Hashtable<String, SymbolTable>();
  }

  /**
   * This method reset the simulator state and global and local symbol tables
   */
  public static void reset() {
    Errors.clear();
    Globals.resetST();
    Globals.resetState();
    System.gc();
  }

}
