package vsim.simulator;

import java.util.HashSet;
import vsim.utils.Message;
import vsim.linker.LinkedProgram;
import vsim.assembler.statements.Statement;


public final class Debugger {

  private static int SPACE = 0;
  private static final String newline = System.getProperty("line.separator");
  private static final String HELP_MSG = "Available commands: " + newline + newline +
                                         // help
                                         "help/?           - show this message" + newline +
                                         // exit
                                         "exit/quit        - exit the simulator" + newline +
                                         // execute previous
                                         "!                - execute previous command" + newline +
                                         // print state
                                         "showx            - print all RVI registers" + newline +
                                         "showf            - print all RVF registers" + newline +
                                         "print reg        - print register reg" + newline +
                                         "memory addr      - print 48 cells of memory at address" + newline +
                                         "memory addr rows - print rows x 4 cells of memory at address" + newline +
                                         // execution
                                         "step             - step the program for 1 instruction" + newline +
                                         "step N           - step the program for N instructions" + newline +
                                         "continue         - continue program execution without stepping" + newline +
                                         "breakpoint addr  - set a breakpoint at address" + newline +
                                         "clear            - clear all breakpoints" + newline +
                                         "delete addr      - delete breakpoint at address" + newline +
                                         "list             - list all breakpoints" + newline +
                                         // reset state
                                         "reset            - reset all state (regs, memory, start)" + newline +
                                         // start address
                                         "start addr       - start the program at address";

  private LinkedProgram program;
  private HashSet breakpoints;
  private int space;

  public Debugger(LinkedProgram program) {
    this.program = program;
    this.breakpoints = new HashSet<Integer>();
    this.space = 1;
    for (Statement stmt: program.getStatements())
      this.space = Math.max(this.space, stmt.getDebugInfo().getSource().length());
  }

  public void interpret(String[] args) {
    if (args[0].equals("exit") || args[0].equals("quit"))
      System.exit(0);
    else if (args[0].equals("help") || args[0].equals("?"))
      System.out.println(HELP_MSG);
    else
      Message.warning("unknown command '" + args[0] + "'");
  }

}
