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
import vsim.Settings;
import vsim.utils.Message;
import vsim.linker.Linker;
import java.util.ArrayList;
import vsim.assembler.Assembler;
import vsim.linker.LinkedProgram;
import vsim.assembler.statements.Statement;


/**
 * The Simulator class contains useful methods to simulate and debug a RISC-V programs.
 */
public final class Simulator {

  /**
   * This method takes an ArrayList of assembler filenames, then calls the assemble
   * and link methods with this to generate a RISC-V linked program and finally
   * simulates that generated linked program.
   *
   * @param files an array of assembler filenames
   * @see vsim.assembler.Assembler#assemble
   * @see vsim.linker.Linker#link
   * @see vsim.linker.LinkedProgram
   */
  public static void simulate(ArrayList<String> files) {
    // clear all
    Globals.reset();
    // assemble -> link -> simulate
    LinkedProgram program = Linker.link(Assembler.assemble(files));
    // set start address
    program.reset();
    // execute all program
    Statement stmt;
    long cycles = 0;
    while ((stmt = program.next()) != null) {
      cycles++;
      Globals.iset.get(stmt.getMnemonic()).execute(stmt.result());
      // if debugging is activated
      if (Settings.DEBUG)
        break;
      // force garbage collection every 1000 cycles, why not ?
      if (Long.remainderUnsigned(cycles, 1000) == 0)
        System.gc();
    }
    if (!Settings.DEBUG) {
      // panic if no exit/exit2 ecall
      String pc = String.format("0x%08x", Globals.regfile.getProgramCounter());
      Message.panic("attempt to execute non-instruction at " + pc);
    } else {
      Message.log("starting debugger...");
      Simulator.debug(program);
    }
  }

  /**
   * This method creates a Debugger for a given linked program.
   *
   * @param program the program to debug
   * @see vsim.simulator.Debugger
   * @see vsim.linker.LinkedProgram
   */
  public static void debug(LinkedProgram program) {
    // create a debugger and run it
    (new Debugger(program)).run();
  }

  /**
   * This method takes an ArrayList of assembler filenames, then calls the assemble
   * and link methods with this to generate a RISC-V linked program and finally
   * calls {@link vsim.simulator.Simulator#debug} to create a Debugger.
   *
   * @param files an array of assembler filenames
   * @see vsim.simulator.Simulator#debug
   * @see vsim.simulator.Debugger
   * @see vsim.linker.LinkedProgram
   */
  public static void debug(ArrayList<String> files) {
    // clear all
    Globals.reset();
    // assemble -> link -> debug
    LinkedProgram program = Linker.link(Assembler.assemble(files));
    // set start address
    program.reset();
    // debug linked program
    Simulator.debug(program);
  }

}
