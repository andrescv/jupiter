package vsim;

import vsim.riscv.Memory;
import java.util.Hashtable;
import java.util.ArrayList;
import vsim.riscv.InstructionSet;
import vsim.riscv.RVIRegisterFile;
import vsim.riscv.RVFRegisterFile;
import vsim.assembler.SymbolTable;


public final class Globals {

  // RAM
  public static final Memory memory = Memory.ram;

  // RVI register file
  public static final RVIRegisterFile regfile = RVIRegisterFile.regfile;

  // RVF register file
  public static final RVFRegisterFile fregfile = RVFRegisterFile.regfile;

  // RV instruction set
  public static final InstructionSet iset = InstructionSet.insts;

  // .globl
  public static final SymbolTable globl = new SymbolTable();

  // .local
  public static Hashtable<String, SymbolTable> local = new Hashtable<String, SymbolTable>();

  // errors
  public static final ArrayList<String> errors = new ArrayList<String>();

  // add error to error list
  public static void error(String msg) {
    // ignore duplicated errors
    if (!Globals.errors.contains(msg))
      Globals.errors.add(msg);
  }

  // reset state
  public static void resetState() {
    // reset memory
    Globals.memory.reset();
    // reset registers
    Globals.regfile.reset();
    // reset floating point registers
    Globals.fregfile.reset();
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
