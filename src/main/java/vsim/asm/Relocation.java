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

package vsim.asm;

import java.io.File;

import vsim.Globals;
import vsim.exc.RelocationException;
import vsim.utils.Data;


/** Assembler relocation function. */
public final class Relocation {

  /** absolute high */
  public static final int HI = 0;
  /** absolute low */
  public static final int LO = 1;
  /** pc-relative */
  public static final int PCREL = 2;
  /** pc-relative high */
  public static final int PCRELHI = 3;
  /** pc-relative low */
  public static final int PCRELLO = 4;
  /** default relocation type */
  public static final int DEFAULT = 5;

  /** relocation file path */
  private final String file;
  /** relocation type */
  private final int type;
  /** relocation target */
  private final String target;

  /**
   * Creates a new relocatio.
   *
   * @param type relocation type
   * @param target relocation target
   */
  public Relocation(File file, int type, String target) {
    this.file = file.getAbsolutePath();
    this.type = type;
    this.target = target;
  }

  /**
   * Tries to resolve the relocation.
   *
   * @param pc current program counter value
   * @return resolved relocation
   */
  public int resolve(int pc) throws RelocationException {
    int address = getAddress();
    int delta = address - pc;
    switch (this.type) {
      case HI:
        return (address >>> 12);
      case LO:
        return address & 0xfff;
      case PCREL:
        return delta;
      case PCRELHI:
        return ((delta >>> 12) + ((delta >>> 11) & 0x1)) & 0xfffff;
      case PCRELLO:
        return Data.signExtend((delta + 4) & 0xfff, 12);
      default:
        return address;
    }
  }

  /**
   * Returns target address.
   *
   * @return target address
   */
  private int getAddress() throws RelocationException {
    if (Globals.local.get(file) != null && Globals.local.get(file).getSymbol(target) != null) {
      return Globals.local.get(file).getSymbol(target).getAddress();
    } else if (Globals.globl.getSymbol(target) != null) {
      return Globals.globl.getSymbol(target).getAddress();
    } else {
      throw new RelocationException(target);
    }
  }

}
