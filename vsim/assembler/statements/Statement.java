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

package vsim.assembler.statements;

import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.MachineCode;


/**
 * The class Statement is used to represent a RISC-V program statement.
 */
public abstract class Statement {

  /** statement mnemonic, e.g add */
  protected String mnemonic;
  /** statement attached debug info */
  protected DebugInfo debug;
  /** statement attached machine code */
  protected MachineCode code;

  /**
   * Unique constructor that initializes a newly Statement object.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   */
  protected Statement(String mnemonic, DebugInfo debug) {
    this.debug = debug;
    this.mnemonic = mnemonic;
    this.code = new MachineCode();
  }

  /**
   * This method tries to resolve all the relocation expansions (if any).
   *
   * @see vsim.linker.Relocation
   */
  public abstract void resolve();

  /**
   * This method tries to build the machine code that represents the statement.
   *
   * @param pc current program counter value
   */
  public abstract void build(int pc);

  /**
   * This method returns the machine code that represents the statement.
   *
   * @see vsim.riscv.instructions.MachineCode
   * @return the machine code
   */
  public MachineCode result() {
    return this.code;
  }

  /**
   * This method returns the mnemonic of the statement.
   *
   * @return the mnemonic
   */
  public String getMnemonic() {
    return this.mnemonic;
  }

  /**
   * This method returns the debug information of the statement.
   *
   * @return the debug information
   */
  public DebugInfo getDebugInfo() {
    return this.debug;
  }

}
