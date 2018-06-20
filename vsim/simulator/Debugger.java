package vsim.simulator;

import vsim.Globals;
import vsim.utils.Data;
import java.util.HashSet;
import vsim.utils.Message;
import vsim.utils.Colorize;
import vsim.linker.LinkedProgram;
import vsim.riscv.instructions.MachineCode;
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
                                         "symbols          - print global symbols" + newline +
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
  private HashSet<Integer> breakpoints;
  private int space;
  private String[] args;

  public Debugger(LinkedProgram program) {
    this.program = program;
    this.breakpoints = new HashSet<Integer>();
    this.space = 1;
    this.args = null;
    for (Statement stmt: program.getStatements())
      this.space = Math.max(this.space, stmt.getDebugInfo().getSource().length());
  }

  private void showx() {
    Globals.regfile.print();
  }

  private void showf() {
    Globals.fregfile.print();
  }

  private void print(String reg) {
    if (Globals.regfile.getRegisterNumber(reg) != -1 || reg.equals("pc"))
      Globals.regfile.printReg(reg);
    else if (Globals.fregfile.getRegisterNumber(reg) != -1)
      Globals.fregfile.printReg(reg);
    else
      Message.warning("invalid register: " + reg);
  }

  private void memory(String address, String rows) {
    int n = 12;
    int addr = Globals.regfile.getRegister("gp");
    try {
      n = Math.max(Integer.parseInt(rows), 1);
    } catch (Exception e ) {/* DO NOTHING */}
    try {
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address);
    } catch (Exception e) {
      Message.warning("invalid address: " + address);
      return;
    }
    Globals.memory.print(addr, n);
  }

  private void symbols() {
    Globals.globl.print();
  }

  private void step(String n) {
    int steps =  1;
    // step [N] ?
    try {
      steps = Math.max(Integer.parseInt(n), 1);
    } catch (Exception e ) {/* DO NOTHING */}
    for (int i = 0; i < steps; i++) {
      boolean breakpoint = false;
      Statement stmt = program.next();
      int pcVal = Globals.regfile.getProgramCounter();
      String pc = String.format("0x%08x", pcVal);
      // no more statements
      if (stmt == null) {
        Message.error("attempt to execute non-instruction at " + pc);
        break;
      }
      // print debugging info
      String space = "";
      MachineCode result = stmt.result();
      String source = stmt.getDebugInfo().getSource();
      // calculate space (pretty print)
      for (int j = 0; j < (this.space - source.length()); j++)
        space += " ";
      System.out.println(
        String.format(
          "PC [%s] CODE:%s    %s %s» %s",
          Colorize.cyan(pc),
          result.toString(),
          Colorize.purple(source),
          space,
          Globals.iset.get(stmt.getMnemonic()).disassemble(result)
        )
      );
      // breakpoint at this point ?
      if (this.breakpoints.contains(pcVal))
        breakpoint = true;
      // execute instruction
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
      // break after execute ?
      if (breakpoint)
        break;
    }
  }

  private void Contine() {
    Statement stmt;
    int pcVal = Globals.regfile.getProgramCounter();
    boolean breakpoint = false;
    while ((stmt = this.program.next()) != null) {
      pcVal = Globals.regfile.getProgramCounter();
      // breakpoint at this point ?
      if (this.breakpoints.contains(pcVal))
        breakpoint = true;
      // execute instruction
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
      // break after execute ?
      if (breakpoint)
        break;
    }
    // only display if no breakpoint was set
    if (!breakpoint) {
      // error if no exit/exit2 ecall
      String pc = String.format("0x%08x", pcVal);
      Message.error("attempt to execute non-instruction at " + pc);
    }
  }

  private void breakpoint(String address) {
    try {
      int addr;
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address);
      if (Data.isWordAligned(addr))
        this.breakpoints.add(addr);
      else
        Message.warning("address is not aligned to a word boundary");
    } catch (Exception e) {
      Message.warning("invalid address: " + address);
    }
  }

  private void clear() {
    this.breakpoints = new HashSet<Integer>();
    System.gc();
  }

  private void delete(String address) {
    int addr;
    try {
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address, 16);
    } catch (Exception e) {
      Message.warning("invalid address: " + address);
      return;
    }
    if (this.breakpoints.contains(addr))
      this.breakpoints.remove(addr);
    else
      Message.warning("no breakpoint at address: " + address);
  }

  private void list() {
    System.out.println("Breakpoints: " + newline);
    for(Object addr: this.breakpoints.toArray())
      System.out.println(Colorize.purple(String.format("    0x%08x", (Integer) addr)));
  }

  private void reset() {
    Globals.resetState();
    this.program.reset();
  }

  private void start(String address) {
    int addr;
    try {
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address, 16);
    } catch (Exception e) {
      Message.warning("invalid address: " + address);
      return;
    }
    if (Data.isWordAligned(addr))
      Globals.regfile.setProgramCounter(addr);
    else
      Message.warning("address is not aligned to a word boundary");
  }

  public void interpret(String[] args) {
    // save previous args
    if (!args[0].equals("!"))
      this.args = args;
    // exit/quit
    if ((args[0].equals("exit") || args[0].equals("quit")))
      System.exit(0);
    // help/?
    else if ((args[0].equals("help") || args[0].equals("?")))
      System.out.println(HELP_MSG);
    // !
    else if (args[0].equals("!")) {
      if (args != null)
        this.interpret(this.args);
    }
    // showx
    else if (args[0].equals("showx"))
      this.showx();
    // showf
    else if (args[0].equals("showf"))
      this.showf();
    // print
    else if (args[0].equals("print")) {
      if (args.length == 2)
        this.print(args[1]);
      else
        Message.warning("invalid usage of print, valid usage 'print reg'");
    }
    else if (args[0].equals("memory")) {
      if (args.length == 2)
        this.memory(args[1], null);
      else if (args.length == 3)
        this.memory(args[1], args[2]);
      else
        Message.warning("invalid usage of memory, valid usage 'memory addr [rows]'");
    }
    else if (args[0].equals("symbols"))
      this.symbols();
    else if (args[0].equals("step")) {
      if (args.length == 1)
        this.step(null);
      else if (args.length == 2)
        this.step(args[1]);
      else
        Message.warning("invalid usage of step, valid usage 'step [N]'");
    }
    // continue
    else if (args[0].equals("continue"))
      this.Contine();
    else if (args[0].equals("breakpoint")) {
      if (args.length == 2)
        this.breakpoint(args[1]);
      else
        Message.warning("invalid usage of breakpoint, valid usage 'breakpoint addr'");
    }
    // clear
    else if (args[0].equals("clear"))
      this.clear();
    // delete addr
    else if (args[0].equals("delete")) {
      if (args.length == 2)
        this.delete(args[1]);
      else
        Message.warning("invalid usage of delete, valid usage 'delete addr'");
    }
    // list
    else if (args[0].equals("list"))
      this.list();
    // reset
    else if (args[0].equals("reset"))
      this.reset();
    // start addr
    else if (args[0].equals("start")) {
      if (args.length == 2)
        this.start(args[1]);
      else
        Message.warning("invalid usage of start, valid usage 'start addr'");
    }
    else
      Message.warning("unknown command '" + args[0] + "'");
  }

}
