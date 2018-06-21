package vsim.linker;

import vsim.Globals;
import vsim.Settings;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Enumeration;
import vsim.assembler.statements.Statement;


public final class LinkedProgram {

  private Hashtable<Integer, Statement> program;

  public LinkedProgram(Hashtable<Integer, Statement> program) {
    this.program = program;
  }

  public Statement next() {
    int pc = Globals.regfile.getProgramCounter();
    return this.program.get(pc);
  }

  public void reset() {
    // set PC to global start label (simulate far-away call)
    int startAddress = Globals.globl.get(Settings.START);
    Globals.regfile.setProgramCounter(startAddress);
  }

  public Collection<Statement> getStatements() {
    return this.program.values();
  }

  public ArrayList<Integer> getBreakpoints() {
    ArrayList<Integer> breakpoints = new ArrayList<Integer>();
    for (Enumeration<Integer> e = this.program.keys(); e.hasMoreElements();) {
      int address = e.nextElement();
      if (this.program.get(address).getMnemonic().equals("ebreak"))
        breakpoints.add(address);
    }
    return breakpoints;
  }

  @Override
  public String toString() {
    return "RISC-V Program";
  }

}
