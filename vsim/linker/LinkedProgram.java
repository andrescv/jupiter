package vsim.linker;

import vsim.Globals;
import java.util.Hashtable;
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

  @Override
  public String toString() {
    return "RISC-V Program";
  }

}
