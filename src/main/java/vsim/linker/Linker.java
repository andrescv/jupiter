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

import java.util.ArrayList;

import vsim.Flags;
import vsim.Globals;
import vsim.asm.Program;
import vsim.asm.Segment;
import vsim.asm.Symbol;
import vsim.asm.stmts.*;
import vsim.exceptions.LinkerException;


/** V-Sim linker. */
public final class Linker {

  /**
   * Links RISC-V assembly programs.
   *
   * @param programs list of unlinked programs
   * @return linked program
   * @throws LinkerException if a linker error occurs
   */
  public static LinkedProgram link(ArrayList<Program> programs) throws LinkerException {
    Symbol start = Globals.globl.getSymbol(Flags.START);
    if (start != null && start.getSegment() == Segment.TEXT) {
      // create a new linked program
      LinkedProgram lp = new LinkedProgram();
      // link .text
      for (Program program : programs) {
        for (Statement stmt : program.text()) {
          lp.add(stmt);
        }
      }
      // link .rodata
      lp.rodataStart();
      for (Program program : programs) {
        for (Byte b : program.rodata()) {
          lp.add(b);
        }
        lp.align();
      }
      lp.rodataEnd();
      // link .bss
      for (Program program : programs) {
        for (Byte b : program.bss()) {
          lp.add(b);
        }
        lp.align();
      }
      // link .data
      for (Program program : programs) {
        for (Byte b : program.data()) {
          lp.add(b);
        }
        lp.align();
      }
      return lp;
    } else if (start != null) {
      throw new LinkerException("global start label '" + Flags.START + "' not defined in .text segment");
    } else {
      throw new LinkerException("global start label '" + Flags.START + "' not defined");
    }
  }

}
