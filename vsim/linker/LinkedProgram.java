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

package vsim.linker;

import vsim.Globals;
import vsim.Settings;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Collection;
import java.util.Enumeration;
import vsim.assembler.statements.Statement;


/**
 * The LinkedProgram class is used to represent a RISC-V linked program.
 */
public final class LinkedProgram {

  /** a history of the program statements */
  private Hashtable<Integer, Statement> program;

  /**
   * Unique constructor that takes a history of statements.
   *
   * @param program A history of statements
   * @see vsim.assembler.statements.Statement
   * @see vsim.linker.Linker
   */
  public LinkedProgram(Hashtable<Integer, Statement> program) {
    this.program = program;
  }

  /**
   * This method returns the next available statement.
   *
   * @see vsim.assembler.statements.Statement
   * @return the next available statement or null if no more available
   */
  public Statement next() {
    int pc = Globals.regfile.getProgramCounter();
    return this.program.get(pc);
  }

  /**
   * This method resets the program, localizes the global start address
   * that is set in VSim settings and makes the program counter point
   * to this address.
   */
  public void reset() {
    // set PC to global start label (simulate far-away call)
    int startAddress = Globals.globl.get(Settings.START);
    Globals.regfile.setProgramCounter(startAddress);
  }

  /**
   * This method returns all the available statements.
   *
   * @see vsim.assembler.statements.Statement
   * @return all the statements available statements
   */
  public Collection<Statement> getStatements() {
    return this.program.values();
  }

  /**
   * This method returns an array of breakpoint addresses, i.g if
   * a program contains ebreak statements these count as breakpoints.
   *
   * @return an array of breakpoint addresses
   */
  public ArrayList<Integer> getBreakpoints() {
    ArrayList<Integer> breakpoints = new ArrayList<Integer>();
    for (Enumeration<Integer> e = this.program.keys(); e.hasMoreElements();) {
      int address = e.nextElement();
      if (this.program.get(address).getMnemonic().equals("ebreak"))
        breakpoints.add(address);
    }
    return breakpoints;
  }

}
