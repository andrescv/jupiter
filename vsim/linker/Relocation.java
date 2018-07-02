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

import vsim.Errors;
import vsim.Globals;
import vsim.utils.Data;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.assembler.SymbolTable;


/**
 * The class Relocation is used to represent a relocation expansion.
 */
public final class Relocation {

  /** a pc-relative high relocation type */
  public static final int PCRELHI = 0;
  /** a pc-relative low relocation type */
  public static final int PCRELLO = 1;
  /** default relocation type */
  public static final int DEFAULT = 2;

  /** relocation type */
  private int type;
  /** relocation target */
  private String target;
  /** debug information */
  private DebugInfo debug;

  /**
   * Unique constructor that takes a relocation type and relocation target.
   *
   * @param type the relocation type
   * @param target the relocation target
   * @param debug debug information
   */
  public Relocation(int type, String target, DebugInfo debug) {
    this.type = type;
    this.target = target;
    this.debug = debug;
  }

  /**
   * This method tries to localize the address of the relocation
   * target in the local symbol table of the program or the global
   * symbol table or creates an error if it is not found.
   *
   * @return the address of the relocation target
   */
  public int getTargetAddress() {
    int address = -1;
    SymbolTable table = Globals.local.get(this.debug.getFilename());
    // local lookup
    if (table != null && table.get(this.target) != null)
      address = table.get(this.target);
    // global lookup
    else if (Globals.globl.get(this.target) != null)
      address = Globals.globl.get(this.target);
    // relocation error
    else
      Errors.add(this.debug, "assembler", "label: '" + this.target + "' used but not defined");
    return address;
  }

  /**
   * This method tries to resolve the relocation.
   *
   * @param pc current program counter value
   * @return the resolved relocation
   */
  public int resolve(int pc) {
    int target = this.getTargetAddress();
    int delta = target - pc;
    // correct target given a relocation type
    switch (this.type) {
      case PCRELHI:
        target = ((delta >>> 12) + ((delta >>> 11) & 0x1)) & (Data.PC_HI_MASK >>> 12);
        break;
      case PCRELLO:
        target = Data.signExtend(((delta + 4) & Data.PC_LO_MASK), 12);
        break;
      case DEFAULT:
        target = delta;
        break;
    }
    return target;
  }

}
