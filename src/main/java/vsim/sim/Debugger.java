/*
Copyright (C) 2018-2019 Andres Castellanos

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

package vsim.sim;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import vsim.Globals;
import vsim.Logger;
import vsim.State;
import vsim.VSim;
import vsim.asm.stmts.Statement;
import vsim.exc.*;
import vsim.linker.LinkedProgram;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;
import vsim.utils.FS;
import vsim.utils.IO;


/** V-Sim debugger. */
public final class Debugger {

  /** previous command */
  private String[] prev;
  /** linked program being debugged */
  private final LinkedProgram program;
  /** simulation history */
  private final History history;
  /** list of breakpoints */
  private final HashMap<Integer, Boolean> breakpoints;
  /** if program is terminated */
  private boolean terminated;

  /**
   * Creates a debugger.
   *
   * @param program linked program to debug
   * @param load load program in memory
   */
  public Debugger(LinkedProgram program, boolean load) {
    this.program = program;
    breakpoints = new HashMap<>();
    history = new History();
    if (load) program.load();
    terminated = false;
    clear();
  }

  /** V-Sim debugger interpreter. */
  public void debug() {
    while (true) {
      IO.stdout().print(">>> ");
      String line = IO.readString(2048);
      if (line.equals("")) {
        continue;
      } else {
        interpret(line.trim().toLowerCase().replaceAll("( |\t)+", " ").split(" "));
      }
    }
  }

  /**
   * Interprets debugger commands.
   *
   * @param args command line arguments
   */
  private void interpret(String[] args) {
    if (args != null) {
      // save previous args
      if (!args[0].equals("!")) {
        prev = args;
      }
      // interpret command
      if (args[0].equals("help") || args[0].equals("?")) {
        help();
      } else if (args[0].equals("exit") || args[0].equals("quit") || args[0].equals("q")) {
        VSim.exit(0);
      } else if (args[0].equals("!")) {
        interpret(prev);
      } else if (args[0].equals("memory")) {
        memory(args);
      } else if (args[0].equals("rvi")) {
        rvi(args);
      } else if (args[0].equals("rvf")) {
        rvf(args);
      } else if (args[0].equals("globals")) {
        globals();
      } else if (args[0].equals("locals")) {
        locals();
      } else if (args[0].equals("step") || args[0].equals("s")) {
        step();
      } else if (args[0].equals("backstep") || args[0].equals("b")) {
        backstep();
      } else if (args[0].equals("continue") || args[0].equals("c")) {
        run();
      } else if (args[0].equals("reset")) {
        reset();
      } else if (args[0].equals("break")) {
        breakpoint(args);
      } else if (args[0].equals("clear")) {
        clear();
      } else if (args[0].equals("delete")) {
        delete(args);
      } else if (args[0].equals("list")) {
        list();
      } else {
        Logger.warning("unk cmd: " + args[0]);
      }
    }
  }

  /** Prints debugger help message */
  private void help() {
    IO.stdout().println("[General Commands]");
    IO.stdout().println(" help/?                - show this help message");
    IO.stdout().println(" exit/quit/q           - exit the simulator and debugger");
    IO.stdout().println(" !                     - execute previous command");
    IO.stdout().println(Data.EOL + "[Display]");
    IO.stdout().println(" rvi                   - print all RVI registers");
    IO.stdout().println(" rvi <reg>             - print RVI register reg");
    IO.stdout().println(" rvf                   - print all RVF registers");
    IO.stdout().println(" rvf <reg>             - print RVF register reg");
    IO.stdout().println(" memory <addr>         - print 12 x 4 cells of memory starting at the given address");
    IO.stdout().println(" memory <addr> <rows>  - print rows x 4 cells of memory starting at the given address");
    IO.stdout().println(" globals               - print global symbol table");
    IO.stdout().println(" locals                - print local symbol tables");
    IO.stdout().println(Data.EOL + "[Execution Control]");
    IO.stdout().println(" step/s                - continue until another instruction reached");
    IO.stdout().println(" backstep/b            - back to the previous instruction");
    IO.stdout().println(" continue/c            - continue running");
    IO.stdout().println(" reset                 - reset");
    IO.stdout().println(Data.EOL + "[Breakpoints]");
    IO.stdout().println(" break <addr>          - set a breakpoint at the given address");
    IO.stdout().println(" clear                 - delete all breakpoints");
    IO.stdout().println(" delete <addr>         - delete breakpoint at the given address");
    IO.stdout().println(" list                  - show defined breakpoints");
  }

  /**
   * Pretty prints memory.
   *
   * @param args command line arguments
   */
  private void memory(String[] args) {
    if (args.length == 2) {
      try {
        program.getState().memory().print(Data.atoi(args[1]), 12);
      } catch (NumberFormatException e) {
        Logger.warning("invalid memory address: " + args[1]);
      }
    } else if (args.length >= 3) {
      int addr = 0;
      int rows = 12;
      try {
        addr = Data.atoi(args[1]);
      } catch (NumberFormatException e) {
        Logger.warning("invalid memory address: " + args[1]);
        return;
      }
      try {
        rows = Data.atoi(args[2]);
      } catch (NumberFormatException e) {
        Logger.warning("invalid number of rows: " + args[2]);
        return;
      }
      program.getState().memory().print(addr, rows);
    } else {
      Logger.warning("memory command expects at least 1 argument, usage: memory address [rows]");
    }
  }

  /** Prints RVI register file. */
  private void rvi(String[] args) {
    if (args.length >= 2) {
      try {
        program.getState().xregfile().print(args[1]);
      } catch (IllegalArgumentException e) {
        Logger.warning("invalid register: " + args[1]);
      }
    } else {
      program.getState().xregfile().print();
    }
  }

  /** Prints RVF register file. */
  private void rvf(String[] args) {
    if (args.length >= 2) {
      try {
        program.getState().fregfile().print(args[1]);
      } catch (IllegalArgumentException e) {
        Logger.warning("invalid register: " + args[1]);
      }
    } else {
      program.getState().fregfile().print();
    }
  }

  /** Pretty prints global symbol table. */
  private void globals() {
    int maxWidth = -1;
    for (String label : Globals.globl.labels()) {
      maxWidth = Math.max(maxWidth, label.length());
    }
    String fmt = "%" + maxWidth + "s -> 0x%08x";
    for (String label : Globals.globl.labels()) {
      IO.stdout().println(String.format(fmt, label, Globals.globl.getSymbol(label).getAddress()));
    }
  }

  /** Pretty prints local symbol table. */
  private void locals() {
    int maxWidth = -1;
    for (File path : Globals.local.keySet()) {
      for (String label : Globals.local.get(path).labels()) {
        maxWidth = Math.max(maxWidth, label.length());
      }
    }
    String fmt = "%" + maxWidth + "s -> 0x%08x";
    for (File path : Globals.local.keySet()) {
      IO.stdout().println(path.toString() + ":");
      for (String label : Globals.local.get(path).labels()) {
        IO.stdout().print(String.format(fmt, label, Globals.local.get(path).getSymbol(label).getAddress()));
        if (Globals.globl.contains(label)) {
          IO.stdout().println(" (global)");
        } else {
          IO.stdout().println();
        }
      }
    }
  }

  /** Continue until another instruction reached. */
  private void step() {
    if (!terminated) {
      try {
        // get next statement
        Statement stmt = program.next();
        // get generated machine code
        MachineCode code = stmt.code();
        // print debug info
        printDebug(stmt);
        // save PC and heap pointer
        history.savePCAndHeap(program.getState());
        // execute instruction
        Globals.iset.get(stmt.mnemonic()).execute(stmt.code(), program.getState());
        // save memory and register files
        history.saveMemAndRegs(program.getState());
      } catch (BreakpointException e) {
        // nothing here :]
        program.getState().xregfile().incProgramCounter();
      } catch (HaltException e) {
        terminated = true;
        IO.stdout().println();
        Logger.info(String.format("exit(%d)", e.getCode()));
      } catch (SimulationException e) {
        terminated = true;
        Logger.error(e.getMessage());
      }
    } else {
      Logger.info("program has finished running, use reset command to execute it again.");
    }
  }

  /** Back to previous instruction. */
  private void backstep() {
    if (!terminated) {
      history.restore(program.getState());
      IO.stdout().println(String.format("PC = 0x%08x", program.getState().xregfile().getProgramCounter()));
    } else {
      Logger.info("program has finished running, use reset command to execute it again.");
    }
  }

  /** Continue running program. */
  private void run() {
    if (!terminated) {
      State state = program.getState();
      while (true) {
        int pc = state.xregfile().getProgramCounter();
        try {
          Statement stmt = program.next();
          // handle breakpoints
          if (breakpoints.containsKey(pc) && breakpoints.get(pc)) {
            breakpoints.put(pc, false);
            break;
          }
          // save PC and heap pointer
          history.savePCAndHeap(program.getState());
          // execute
          Globals.iset.get(stmt.mnemonic()).execute(stmt.code(), state);
          // save memory and register files
          history.saveMemAndRegs(program.getState());
          // restore breakpoint state
          if (breakpoints.containsKey(pc)) {
            breakpoints.put(pc, true);
          }
        } catch (BreakpointException e) {
          state.xregfile().incProgramCounter();
          breakpoints.put(pc, true);
        } catch (HaltException e) {
          terminated = true;
          IO.stdout().println();
          Logger.info(String.format("exit(%d)", e.getCode()));
          break;
        } catch (SimulationException e) {
          terminated = true;
          Logger.error(e.getMessage());
        }
      }
    } else {
      Logger.info("program has finished running, use reset command to execute it again.");
    }
  }

  /** Resets program execution. */
  private void reset() {
    terminated = false;
    history.reset();
    program.getState().reset();
    program.load();
  }

  /** Sets a breakpoint. */
  private void breakpoint(String[] args) {
    if (args.length >= 2) {
      try {
        int addr = Data.atoi(args[1]);
        if (Data.isWordAligned(addr)) {
          breakpoints.put(addr, true);
        } else {
          Logger.warning("address is not aligned to a word boundary");
        }
      } catch (NumberFormatException e) {
        Logger.warning("invalid address: " + args[1]);
      }
    } else {
      Logger.warning("break command expects 1 argument, usage: break addr");
    }
  }

  /** clears all breakpoints. */
  private void clear() {
    breakpoints.clear();
    // restore ebreak instructions
    for (Integer addr : program.breaks()) {
      breakpoints.put(addr, true);
    }
  }

  /** deletes a breakpoint. */
  private void delete(String[] args) {
    if (args.length >= 2) {
      try {
        int addr = Data.atoi(args[1]);
        if (breakpoints.containsKey(addr)) {
          breakpoints.remove(addr);
        } else {
          Logger.warning("no breakpoint at address: " + addr);
        }
      } catch (NumberFormatException e) {
        Logger.warning("invalid address: " + args[1]);
      }
    } else {
      Logger.warning("delete command expects 1 argument, usage: delete addr");
    }
  }

  /** list all breakpoints */
  private void list() {
    if (breakpoints.size() > 0) {
      IO.stdout().println("Breakpoints:" + Data.EOL);
      for (Integer addr : breakpoints.keySet()) {
        IO.stdout().println(String.format("@  0x%08x", addr));
      }
    } else {
      Logger.info("no breakpoints yet");
    }
  }

  /**
   * Pretty prints debug info.
   *
   * @param stmt statement
   */
  private void printDebug(Statement stmt) {
    String fmt = "[0x%08x] (0x%08x) %s";
    int pc = program.getState().xregfile().getProgramCounter();
    int code = stmt.code().bits();
    String basic = Globals.iset.get(stmt.mnemonic()).disassemble(stmt.code());
    // get source line
    String source = null;
    File file = stmt.getFile();
    int line = stmt.getLine();
    if (line > 0) {
      try {
        source = FS.getLine(file, line);
        fmt = (source != null) ? fmt + " > %s" : fmt;
        source = (source == null) ? basic : source;
        // remove comments
        source = source.replaceAll("[;#].*", "");
        // normalize whitespace (tabs, spaces)
        source = source.replaceAll("( |\t)+", " ");
        // normalize commas
        source = source.replaceAll("( )?,( )?", ", ");
        // trim whitespace
        source = source.trim();
      } catch (IOException e) {
        source = basic;
      }
    } else {
      source = basic;
    }
    IO.stdout().println(String.format(fmt, pc, code, source, basic));
  }

}
