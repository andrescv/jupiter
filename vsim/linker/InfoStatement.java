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

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import vsim.Globals;
import vsim.assembler.statements.Statement;
import vsim.riscv.instructions.MachineCode;


/**
 * This class represents the info of a statement, only useful for GUI application.
 */
public final class InfoStatement {

  /** if the statement is a ebreak statement */
  private final boolean ebreak;
  /** if the statement has a breakpoint */
  private final SimpleBooleanProperty breakpoint;
  /** address where the statement was placed in memory */
  private final SimpleStringProperty address;
  /** machine code of the statement */
  private final SimpleStringProperty machineCode;
  /** line source code */
  private final SimpleStringProperty sourceCode;
  /** basic code */
  private final SimpleStringProperty basicCode;

  /**
   * Creates a info statement.
   *
   * @param address memory address where statement was placed in memory
   * @param stmt source statement
   */
  public InfoStatement(int address, Statement stmt) {
    MachineCode result = stmt.result();
    this.ebreak = stmt.getMnemonic().equals("ebreak");
    this.breakpoint = new SimpleBooleanProperty(this.ebreak);
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
    this.machineCode = new SimpleStringProperty(result.toString());
    this.sourceCode = new SimpleStringProperty(stmt.getDebugInfo().getSource());
    this.basicCode = new SimpleStringProperty(Globals.iset.get(stmt.getMnemonic()).disassemble(result));
  }

  /**
   * Gets the breakpoint property.
   *
   * @return breakpoint property
   */
  public SimpleBooleanProperty breakpointProperty() {
    return this.breakpoint;
  }

  /**
   * Gets the breakpoint property value.
   *
   * @return true if the statement has a breakpoint, false if not
   */
  public boolean getBreakpoint() {
    return this.breakpoint.get();
  }

  /**
   * Gets if the statement is a ebreak statement.
   *
   * @return true if the statement is a ebreak, false if not
   */
  public boolean isEbreak() {
    return this.ebreak;
  }

  /**
   * Sets breakpoint property boolean value.
   *
   * @param val boolean value
   */
  public void setBreakpoint(boolean val) {
    this.breakpoint.set(val);
  }

  /**
   * Gets the memory address.
   *
   * @return memory address of the statement
   */
  public String getAddress() {
    return this.address.get();
  }

  /**
   * Gets the generated machine code.
   *
   * @return machine code of the statement
   */
  public String getMachineCode() {
    return this.machineCode.get();
  }

  /**
   * Gets the source code.
   *
   * @return source code of the statement
   */
  public String getSourceCode() {
    return this.sourceCode.get();
  }

  /**
   * Gets the basic code.
   *
   * @return basic (TAL) code
   */
  public String getBasicCode() {
    return this.basicCode.get();
  }

}
