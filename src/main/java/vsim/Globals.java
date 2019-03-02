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

import java.util.Hashtable;
import vsim.assembler.SymbolTable;
import vsim.gui.components.ExceptionDialog;
import vsim.riscv.InstructionSet;
import vsim.riscv.hardware.Memory;
import vsim.riscv.hardware.RVFRegisterFile;
import vsim.riscv.hardware.RVIRegisterFile;
import vsim.utils.Stats;


/**
 * The Globals class contains a collection of globally-available data structures.
 */
public final class Globals {

  /** V-Sim current version */
  public static final String VERSION = "v2.0.2";

  /** V-Sim Copyright */
  public static final String COPYRIGHT = "Copyright (c) 2018-2019 Andres Castellanos";

  /** Help URL */
  public static final String HELP = "https://git.io/fhnyL";

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

  /** Instruction statistics */
  public static final Stats stats = new Stats();

  /** local symbol table per file (used for debugging and local symbols) */
  public static Hashtable<String, SymbolTable> local = new Hashtable<String, SymbolTable>();

  /** Exception dialog available only in GUI */
  public static ExceptionDialog exceptionDialog = null;

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
    // reset stats
    Globals.stats.reset();
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
  }

}
