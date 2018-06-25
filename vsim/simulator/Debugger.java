/*
Copyright (C) 2018 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

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


/**
 * The class Debugger implements a simple debugger for RISC-V programs.
 */
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
                                         // reset state and start again
                                         "reset             - reset all state (regs, memory, start)";

  /** a linked program to debug */
  private LinkedProgram program;
  /** a history of breakpoints */
  private Hashtable<Integer, Boolean> breakpoints;
  /** calculated space for pretty printed statements */
  private int space;
  /** previous command */
  private String[] args;

  /**
   * Unique constructor that takes a linked program
   *
   * @param program the linked program
   * @see vsim.linker.LinkedProgram
   */
  public Debugger(LinkedProgram program) {
    this.program = program;
    this.breakpoints = new Hashtable<Integer, Boolean>();
    this.space = 1;
    this.args = null;
    for (Statement stmt: program.getStatements())
      this.space = Math.max(this.space, stmt.getDebugInfo().getSource().length());
    // set program breakpoints
    for (Integer breakpoint: program.getBreakpoints())
      this.breakpoints.put(breakpoint, true);
  }

  /**
   * This method prints the RVI register file.
   *
   * @see vsim.riscv.RVIRegisterFile
   */
  private void showx() {
    Globals.regfile.print();
  }

  /**
   * This method prints the RVF register file.
   *
   * @see vsim.riscv.RVFRegisterFile
   */
  private void showf() {
    Globals.fregfile.print();
  }

  /**
   * This method tries to print a register of the RVI or RVF register file.
   *
   * @param reg the register to print
   * @see vsim.riscv.RVIRegisterFile
   * @see vsim.riscv.RVFRegisterFile
   */
  private void print(String reg) {
    if (Globals.regfile.getRegisterNumber(reg) != -1 || reg.equals("pc"))
      Globals.regfile.printReg(reg);
    else if (Globals.fregfile.getRegisterNumber(reg) != -1)
      Globals.fregfile.printReg(reg);
    else
      Message.error("invalid register: " + reg);
  }

  /**
   * This methods prints a portion of the RISC-V memory
   *
   * @param address The address to start printing memory in hex or decimal
   * @param rows How many rows of 4 memory cells to print
   * @see vsim.riscv.Memory
   */
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

  /**
   * This method prints the global symbol table.
   *
   * @see vsim.Globals#globl
   */
  private void symbols() {
    Globals.globl.print();
  }

  /**
   * This method tries to step the program by one statement and pretty prints
   * useful debug information.
   */
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
    Globals.iset.get(stmt.getMnemonic()).execute(result);
    // reset breakpoint
    if (this.breakpoints.containsKey(pcVal))
      this.breakpoints.put(pcVal, true);
  }

  /**
   * This method continues the program execution until a breakpoint or
   * no more available statements are found.
   */
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

  /**
   * This method tries to create a breakpoint at the given an address.
   *
   * @param address the address of the breakpoint in hex or decimal
   */
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

  /**
   * This method clears all the breakpoints that a user set.
   */
  private void clear() {
    this.breakpoints.clear();
    System.gc();
  }

  /**
   * This method tries to delete a breakpoint that a user set.
   *
   * @param address a string representing the address in hex or decimal
   */
  private void delete(String address) {
    int addr;
    try {
      if (address.startsWith("0x"))
        addr = Integer.parseInt(address.substring(2), 16);
      else
        addr = Integer.parseInt(address);
    } catch (Exception e) {
      Message.error("invalid address: " + address);
      return;
    }
    if (this.breakpoints.containsKey(addr))
      this.breakpoints.remove(addr);
    else
      Message.error("no breakpoint at address: " + address);
  }

  /**
   * This method lists the breakpoints that the user set.
   */
  private void list() {
    if (this.breakpoints.size() > 0) {
      System.out.println("Breakpoints: " + newline);
      for(Enumeration<Integer> e = this.breakpoints.keys(); e.hasMoreElements();)
        System.out.println(Colorize.purple(String.format("    0x%08x", e.nextElement())));
    } else
      System.out.println("no breakpoints yet");
  }

  /**
   * This method resets the program and the state of the simulator.
   */
  private void reset() {
    Globals.resetState();
    this.program.reset();
  }

  /**
   * This method takes an array of arguments and tries to match this
   * with an available debug command and interprets it.
   *
   * @param args the command arguments
   */
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
    else
      Message.error("unknown command '" + args[0] + "'");
  }

}
