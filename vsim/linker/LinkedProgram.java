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

package vsim.linker;

import static vsim.riscv.MemorySegments.TEXT_SEGMENT_BEGIN;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vsim.Globals;
import vsim.assembler.statements.Statement;
import vsim.riscv.exceptions.NonInstructionException;
import vsim.utils.Data;


/**
 * The LinkedProgram class is used to represent a RISC-V linked program.
 */
public final class LinkedProgram {

  /** a history of the program statements */
  private HashMap<Integer, Statement> program;

  /**
   * Unique constructor that takes a history of statements.
   *
   * @param program A history of statements
   * @see vsim.assembler.statements.Statement
   * @see vsim.linker.Linker
   */
  public LinkedProgram(HashMap<Integer, Statement> program) {
    this.program = program;
  }

  /**
   * This method returns the next available statement.
   *
   * @see vsim.assembler.statements.Statement
   * @return the next available statement or null if no more available
   */
  public Statement next() throws NonInstructionException {
    // fecth next statement
    Statement stmt = this.program.get(Globals.regfile.getProgramCounter());
    // non instruction exception
    if (stmt == null)
      throw new NonInstructionException();
    return stmt;
  }

  /**
   * This method resets the program, setting the program counter equal to the beginning of the text segment.
   */
  public void reset() {
    Globals.regfile.setProgramCounter(TEXT_SEGMENT_BEGIN);
  }

  /**
   * This method returns the statement at the given address.
   *
   * @param address address of the statement
   * @return statement at the given address or null if no statement at that address
   */
  public Statement getStatement(int address) {
    return this.program.get(address);
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
   * This method returns an array of breakpoint addresses, i.g if a program contains ebreak statements these count as
   * breakpoints.
   *
   * @return an array of breakpoint addresses
   */
  public ArrayList<Integer> getBreakpoints() {
    ArrayList<Integer> breakpoints = new ArrayList<Integer>();
    for (Integer address : this.program.keySet()) {
      if (this.program.get(address).getMnemonic().equals("ebreak"))
        breakpoints.add(address);
    }
    breakpoints.trimToSize();
    return breakpoints;
  }

  /**
   * Gets observable list of info statements.
   *
   * @return observable list of info statements
   */
  public ObservableList<InfoStatement> getInfoStatements() {
    ObservableList<InfoStatement> stmts = FXCollections.observableArrayList();
    int pc = TEXT_SEGMENT_BEGIN;
    while (true) {
      Statement stmt = this.program.get(pc);
      if (stmt == null)
        break;
      else
        stmts.add(new InfoStatement(pc, stmt));
      pc += Data.WORD_LENGTH;
    }
    return stmts;
  }

}
