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

package jupiter.riscv.instructions;

import jupiter.exc.SimulationException;
import jupiter.sim.State;


/** Represents an abstract instruction implementation. */
public abstract class Instruction {

  /** instruction format */
  private final Format format;
  /** instruction mnemonic */
  private final String mnemonic;
  /** instruction opcode */
  private final int opcode;
  /** instruction funct3 */
  private final int funct3;
  /** instruction funct7 */
  private final int funct7;

  /**
   * Creates a new abstract instruction.
   *
   * @param format the format of the instruction
   * @param mnemonic the instruction mnemonic
   * @param opcode instruction opcode field
   * @param funct3 instruction funct3 field
   * @param funct7 instruction funct7 field
   */
  protected Instruction(Format format, String mnemonic, int opcode, int funct3, int funct7) {
    this.format = format;
    this.mnemonic = mnemonic;
    this.opcode = opcode;
    this.funct3 = funct3;
    this.funct7 = funct7;
  }

  /**
   * Simulates an instruction.
   *
   * @param code machine code to execute
   * @param state program state
   * @throws JupiterException if an exception occurs while simulating the instruction
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
    return opcode;
  }

  /**
   * Returns the instruction funct3 field.
   *
   * @return the instruction funct3 field
   */
  public int getFunct3() {
    return funct3;
  }

  /**
   * Returns the instruction funct7 field.
   *
   * @return the instruction funct7 field
   */
  public int getFunct7() {
    return funct7;
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
