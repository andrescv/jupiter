package vsim.simulator;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Message;
import java.util.Hashtable;
import vsim.utils.Colorize;
import java.util.Enumeration;
import vsim.linker.LinkedProgram;
import vsim.riscv.instructions.MachineCode;
import vsim.assembler.statements.Statement;


public final class Debugger {

  // help message
  private static final String newline = System.getProperty("line.separator");
  private static final String HELP_MSG = "Available commands: " + newline + newline +
                                         // help
                                         "help/?            - show this message" + newline +
                                         // exit
                                         "exit/quit         - exit the simulator" + newline +
                                         // execute previous
                                         "!                 - execute previous command" + newline +
                                         // print state
                                         "showx             - print all RVI registers" + newline +
                                         "showf             - print all RVF registers" + newline +
                                         "print reg         - print register reg" + newline +
                                         "memory addr       - print 48 cells of memory at address" + newline +
                                         "memory addr rows  - print rows x 4 cells of memory at address" + newline +
                                         "symbols           - print global symbols" + newline +
                                         // execution
                                         "step/s            - step the program for 1 instruction" + newline +
                                         "continue/c        - continue program execution without stepping" + newline +
                                         "breakpoint/b addr - set a breakpoint at address" + newline +
                                         "clear/cl          - clear all breakpoints" + newline +
                                         "delete/del addr   - delete breakpoint at address" + newline +
                                         "list/ls           - list all breakpoints" + newline +
                                         // reset state
                                         "reset             - reset all state (regs, memory, start)" + newline +
                                         // start address
                                         "start addr        - start the program at address";

  private LinkedProgram program;
  private Hashtable<Integer, Boolean> breakpoints;
  private int space;
  private String[] args;

  public Debugger(LinkedProgram program) {
    this.program = program;
    this.breakpoints = new Hashtable<Integer, Boolean>();
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
      Message.error("invalid register: " + reg);
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
      Message.error("invalid address: " + address);
      return;
    }
    Globals.memory.print(addr, n);
  }

  private void symbols() {
    Globals.globl.print();
  }

  private void step() {
    Statement stmt = program.next();
    int pcVal = Globals.regfile.getProgramCounter();
    String pc = String.format("0x%08x", pcVal);
    // no more statements
    if (stmt == null) {
      Message.error("attempt to execute non-instruction at " + pc);
      return;
    }
    // print debugging info
    String space = "";
    MachineCode result = stmt.result();
    String source = stmt.getDebugInfo().getSource();
    // calculate space (pretty print)
    for (int j = 0; j < (this.space - source.length()); j++)
      space += " ";
    // format all debugging info
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
    // execute instruction
    Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
    // reset breakpoint
    if (this.breakpoints.containsKey(pcVal))
      this.breakpoints.put(pcVal, true);
  }

  private void forward() {
    Statement stmt;
    int pcVal = Globals.regfile.getProgramCounter();
    while ((stmt = this.program.next()) != null) {
      // get actual program counter
      pcVal = Globals.regfile.getProgramCounter();
      // breakpoint at this point ?
      if (this.breakpoints.containsKey(pcVal) && this.breakpoints.get(pcVal)) {
        this.breakpoints.put(pcVal, false);
        return;
      }
      // execute instruction
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
      // reset breakpoint
      if (this.breakpoints.containsKey(pcVal))
        this.breakpoints.put(pcVal, true);
    }
    // error if no exit/exit2 ecall
    String pc = String.format("0x%08x", pcVal);
    Message.error("attempt to execute non-instruction at " + pc);
  }

  private void breakpoint(String address) {
    try {
      int addr;
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address);
      if (Data.isWordAligned(addr)) {
        if (!this.breakpoints.containsKey(addr))
          this.breakpoints.put(addr, true);
      } else
        Message.error("address is not aligned to a word boundary");
    } catch (Exception e) {
      Message.error("invalid address: " + address);
    }
  }

  private void clear() {
    this.breakpoints.clear();
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
      Message.error("invalid address: " + address);
      return;
    }
    if (this.breakpoints.containsKey(addr))
      this.breakpoints.remove(addr);
    else
      Message.error("no breakpoint at address: " + address);
  }

  private void list() {
    if (this.breakpoints.size() > 0) {
      System.out.println("Breakpoints: " + newline);
      for(Enumeration<Integer> e = this.breakpoints.keys(); e.hasMoreElements();)
        System.out.println(Colorize.purple(String.format("    0x%08x", e.nextElement())));
    } else
      System.out.println("no breakpoints yet");
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
      Message.error("invalid address: " + address);
      return;
    }
    if (Data.isWordAligned(addr))
      Globals.regfile.setProgramCounter(addr);
    else
      Message.error("address is not aligned to a word boundary");
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
      if (args.length > 1)
        this.print(args[1]);
      else
        Message.error("invalid usage of print cmd, valid usage 'print reg'");
    }
    // memory
    else if (args[0].equals("memory")) {
      if (args.length == 2)
        this.memory(args[1], null);
      else if (args.length > 2)
        this.memory(args[1], args[2]);
      else
        Message.error("invalid usage of memory cmd, valid usage 'memory addr [rows]'");
    }
    // symbols
    else if (args[0].equals("symbols"))
      this.symbols();
    // step
    else if (args[0].equals("step") || args[0].equals("s"))
      this.step();
    // continue
    else if (args[0].equals("continue")  || args[0].equals("c"))
      this.forward();
    // breakpoint
    else if (args[0].equals("breakpoint") || args[0].equals("b")) {
      if (args.length > 1)
        this.breakpoint(args[1]);
      else
        Message.error("invalid usage of breakpoint cmd, valid usage 'breakpoint/b addr'");
    }
    // clear
    else if (args[0].equals("clear") || args[0].equals("cl"))
      this.clear();
    // delete addr
    else if (args[0].equals("delete") || args[0].equals("del")) {
      if (args.length > 1)
        this.delete(args[1]);
      else
        Message.error("invalid usage of delete cmd, valid usage 'delete/del addr'");
    }
    // list
    else if (args[0].equals("list") || args[0].equals("ls"))
      this.list();
    // reset
    else if (args[0].equals("reset"))
      this.reset();
    // start addr
    else if (args[0].equals("start")) {
      if (args.length > 1)
        this.start(args[1]);
      else
        Message.error("invalid usage of start cmd, valid usage 'start addr'");
    }
    else
      Message.error("unknown command '" + args[0] + "'");
  }

}
