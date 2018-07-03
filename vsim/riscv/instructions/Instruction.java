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

package vsim.riscv.instructions;

import vsim.utils.Data;
import vsim.utils.Colorize;


/**
 * The class Instruction represents an abstract instruction implementation.
 */
public abstract class Instruction {

  /** instruction length in bytes */
  public static final int LENGTH = Data.WORD_LENGTH;

  /** instruction format */
  private Format format;
  /** instruction mnemonic */
  private String mnemonic;
  /** instruction usage */
  private String usage;
  /** instruction description */
  private String description;

  /**
   * Unique constructor that initializes a newly Instruction object.
   *
   * @param format the format of the instruction
   * @param mnemonic the instruction mnemonic
   * @param usage the instruction usage
   * @param description the instruction description
   */
  protected Instruction(Format format, String mnemonic, String usage, String description) {
    this.format = format;
    this.mnemonic = mnemonic;
    this.usage = usage;
    this.description = description;
  }

  /**
   * This method simulates an instruction.
   *
   * @param code machine code to execute
   */
  public abstract void execute(MachineCode code);

  /**
   * This method returns the disassembled String representation
   * of the code.
   *
   * @param code machine code to disassemble
   * @return a String with the disassembled representation
   */
  public abstract String disassemble(MachineCode code);

  /**
   * This method returns the format of the instruction.
   *
   * @return the instruction format
   */
  public Format getFormat() {
    return this.format;
  }

  /**
   * This method returns the instruction opcode field.
   *
   * @return the instruction opcode
   */
  public int getOpCode() {
    return 0;
  }

  /**
   * This method returns the instruction funct3 field.
   *
   * @return the instruction funct3
   */
  public int getFunct3() {
    return 0;
  }

  /**
   * This method returns the instruction funct7 field.
   *
   * @return the instruction funct7
   */
  public int getFunct7() {
    return 0;
  }

  /**
   * This method returns the instruction mnemonic.
   *
   * @return the instruction mnemonic
   */
  public String getMnemonic() {
    return this.mnemonic;
  }

  /**
   * This method returns the instruction usage.
   *
   * @return the instruction usage
   */
  public String getUsage() {
    return this.usage;
  }

  /**
   * This method returns the instruction description.
   *
   * @return the instruction description
   */
  public String getDescription() {
    return this.description;
  }

}
