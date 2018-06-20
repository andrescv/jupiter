package vsim.simulator;

import vsim.Globals;
import vsim.utils.Cmd;
import vsim.utils.Message;
import vsim.linker.Linker;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import vsim.assembler.Assembler;
import java.io.InputStreamReader;
import vsim.linker.LinkedProgram;
import vsim.assembler.statements.Statement;


public final class Simulator {

  public static void simulate(ArrayList<String> files) {
    // clear all
    Globals.reset();
    // assemble -> link -> simulate
    LinkedProgram program = Linker.link(Assembler.assemble(files));
    // set start address
    program.reset();
    // execute all program
    Statement stmt;
    while ((stmt = program.next()) != null)
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
    // panic if no exit/exit2 ecall
    String pc = String.format("0x%08x", Globals.regfile.getProgramCounter());
    Message.panic("attempt to execute non-instruction at " + pc);
  }

  public static void debug(LinkedProgram program) {
    // set start address
    program.reset();
    // create a debugger
    Debugger debugger = new Debugger(program);
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      Cmd.prompt();
      try {
        // read a line from stdin
        String line = br.readLine();
        if (line == null) { System.out.println(); continue; }
        if (line.equals("")) continue;
        // interpret line
        debugger.interpret(line.trim().toLowerCase().split(" "));
      } catch (IOException e) {
        Message.panic("input could not be read");
      } catch (Exception e) {
        Message.panic("unexpected exception");
      }
    }
  }

  public static void debug(ArrayList<String> files) {
    // clear all
    Globals.reset();
    // assemble -> link -> debug
    LinkedProgram program = Linker.link(Assembler.assemble(files));
    // debug linked program
    Simulator.debug(program);
  }

}
