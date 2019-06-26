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

package vsim.asm.stmts;

import java.nio.file.Path;

import vsim.exceptions.AssemblerException;
import vsim.riscv.instructions.MachineCode;


/** General representation of a RISC-V statement. */
public abstract class Statement {

  /** statement file path */
  protected final Path file;
  /** statement line number */
  protected final int line;
  /** statement mnemonic, e.g add */
  protected final String mnemonic;
  /** statement generated machine code */
  protected final MachineCode code;

  /**
   * Creates a new RISC-V statement.
   *
   * @param file statement file path
   * @param line statement line number
   * @param mnemonic statement mnemonic
   */
  protected Statement(Path file, int line, String mnemonic) {
    this.file = file;
    this.line = line;
    this.mnemonic = mnemonic;
    code = new MachineCode();
  }

  /**
   * Creates a new RISC-V statement from machine code.
   *
   * @param mnemonic instruction mnemonic
   * @param code generated machine code
   */
  public Statement(String mnemonic, MachineCode code) {
    this.mnemonic = mnemonic;
    this.code = code;
    line = -1;
    file = null;
  }

  /**
   * Verifies if the statement can generate machine code.
   *
   * @throws AssemblerException if the statement can not generate machine code
   */
  public abstract void check() throws AssemblerException;

  /**
   * Tries to generate machine code that represents the statement.
   *
   * @param pc current program counter value
   * @throws AssemblerException if the statement can not generate machine code
   */
  public abstract void build(int pc) throws AssemblerException;

  /**
   * Returns statement file path.
   *
   * @return statement file path
   */
  public Path getFile() {
    return file;
  }

  /**
   * Returns statement line number.
   *
   * @return statement line number
   */
  public int getLine() {
    return line;
  }

  /**
   * Returns the machine code that represents the statement.
   *
   * @return the machine code
   */
  public MachineCode code() {
    return code;
  }

  /**
   * Returns the statement mnemonic.
   *
   * @return statement mnemonic
   */
  public String mnemonic() {
    return mnemonic;
  }

}
