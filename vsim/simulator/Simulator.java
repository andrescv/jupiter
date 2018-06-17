package vsim.simulator;

import vsim.Globals;
import vsim.utils.Message;
import vsim.linker.Linker;
import java.util.ArrayList;
import vsim.assembler.Assembler;
import vsim.linker.LinkedProgram;
import vsim.assembler.statements.Statement;


public final class Simulator {

  public static void simulate(ArrayList<String> files) {
    // assemble -> link -> simulate
    LinkedProgram program = Linker.link(Assembler.assemble(files));
    // execute all program
    Statement stmt;
    while ((stmt = program.next()) != null) {
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
    }
    // panic if no exit/exit2 ecall
    String pc = String.format("0x%08x", Globals.regfile.getProgramCounter());
    Message.panic("attempt to execute non-instruction at " + pc);
  }

}
