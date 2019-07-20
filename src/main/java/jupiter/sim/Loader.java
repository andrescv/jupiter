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

package jupiter.sim;

import java.io.File;
import java.util.ArrayList;

import jupiter.Flags;
import jupiter.Jupiter;
import jupiter.Logger;
import jupiter.asm.Assembler;
import jupiter.exc.*;
import jupiter.linker.Linker;


/** Jupiter CLI loader. */
public final class Loader {

  /**
   * Loads simulator or debugger.
   *
   * @param files RISC-V assembly files
   */
  public static void load(ArrayList<File> files) {
    try {
      // debug or simulate ?
      if (Flags.DEBUG) {
        (new Debugger(Linker.link(Assembler.assemble(files)))).debug();
      } else {
        (new Simulator(Linker.link(Assembler.assemble(files)))).simulate();
      }
    } catch (AssemblerException e) {
      Logger.error(e.getMessage());
      Jupiter.exit(1);
    } catch (LinkerException e) {
      Logger.error(e.getMessage());
      Jupiter.exit(1);
    }
  }

}
