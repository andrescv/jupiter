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

package vsim.riscv.instructions;

import vsim.State;
import vsim.exc.SimulationException;


/** Represents an abstract instruction implementation. */
public abstract class Instruction {

  /** instruction format */
  private Format format;
  /** instruction mnemonic */
  private String mnemonic;

  /**
   * Creates a new abstract instruction.
   *
   * @param format the format of the instruction
   * @param mnemonic the instruction mnemonic
   */
  protected Instruction(Format format, String mnemonic) {
    this.format = format;
    this.mnemonic = mnemonic;
  }

  /**
   * Simulates an instruction.
   *
   * @param code machine code to execute
   * @param state program state
   * @throws VSimException if an exception occurs while simulating the instruction
   */
  public abstract void execute(MachineCode code, State state) throws SimulationException;

  /**
   * Returns the disassembled String representation of the given machine code.
   *
   * @param code machine code to disassemble
   * @return a String with the disassembled representation
   */
  public abstract String disassemble(MachineCode code);

  /**
   * Returns the instruction format.
   *
   * @return the instruction format
   */
  public Format getFormat() {
    return this.format;
  }

  /**
   * Returns the instruction opcode field.
   *
   * @return the instruction opcode field
   */
  public int getOpCode() {
    return 0;
  }

  /**
   * Returns the instruction funct3 field.
   *
   * @return the instruction funct3 field
   */
  public int getFunct3() {
    return 0;
  }

  /**
   * Returns the instruction funct7 field.
   *
   * @return the instruction funct7 field
   */
  public int getFunct7() {
    return 0;
  }

  /**
   * Returns the instruction mnemonic.
   *
   * @return the instruction mnemonic
   */
  public String getMnemonic() {
    return mnemonic;
  }

}
