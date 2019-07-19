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

package jvpiter.riscv.instructions;


/** Represents the generated machine code of an instruction. */
public final class MachineCode {

  /** machine code */
  private int code;

  /**
   * Creates a initialized machine code.
   *
   * @param code initial machine code
   */
  public MachineCode(int code) {
    this.code = code;
  }

  /**
   * Creates a new machine code initialized with {@code 0x00000000}.
   */
  public MachineCode() {
    this(0x0);
  }

  /**
   * Sets the given instruction field with a value.
   *
   * @param field the instruction field to set
   * @param value the new field value
   */
  public void set(InstructionField field, int value) {
    code = (code & ~(field.mask << field.lo)) | ((value & field.mask) << field.lo);
  }

  /**
   * Returns the value of the given instruction field.
   *
   * @param field the instruction field to get
   * @return instruction field value
   */
  public int get(InstructionField field) {
    return (code >>> field.lo) & field.mask;
  }

  /**
   * Returns all machine code bits.
   *
   * @return all machine code bits
   */
  public int bits() {
    return code;
  }

}
