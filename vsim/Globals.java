package vsim;

import vsim.riscv.Memory;
import java.util.Hashtable;
import java.util.ArrayList;
import vsim.riscv.RegisterFile;
import vsim.riscv.InstructionSet;
import vsim.assembler.SymbolTable;


public final class Globals {

  // RAM
  public static final Memory memory = Memory.ram;

  // RV register file
  public static final RegisterFile regfile = RegisterFile.regfile;

  // RV instruction set
  public static final InstructionSet iset = InstructionSet.insts;

  // .globl
  public static final SymbolTable globl = new SymbolTable();

  // .local
  public static Hashtable<String, SymbolTable> local = new Hashtable<String, SymbolTable>();

  // errors
  public static final ArrayList<String> errors = new ArrayList<String>();

  // reset state
  public static void resetState() {
    // reset memory
    Globals.memory.reset();
    // reset registers
    Globals.regfile.reset();
  }

  // reset symbol table
  public static void resetST() {
    Globals.globl.reset();
    Globals.local = new Hashtable<String, SymbolTable>();
  }

  // hard reset
  public static void reset() {
    Globals.resetST();
    Globals.resetState();
    Globals.errors.clear();
    System.gc();
  }

}
