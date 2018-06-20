package vsim.linker;

import vsim.Globals;
import vsim.Settings;
import java.util.Hashtable;
import java.util.Collection;
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

  @Override
  public String toString() {
    return "RISC-V Program";
  }

}
