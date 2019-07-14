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

import java.io.IOException;
import java.util.ArrayList;

import vsim.Flags;
import vsim.Globals;
import vsim.Logger;
import vsim.VSim;
import vsim.asm.Program;
import vsim.asm.Segment;
import vsim.asm.Symbol;
import vsim.asm.stmts.Statement;
import vsim.exc.LinkerException;
import vsim.utils.Dump;


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
      // dump
      if (Flags.DUMP_CODE != null || Flags.DUMP_DATA != null) {
        // machine code
        if (Flags.DUMP_CODE != null) {
          try {
            Dump.dumpCode(Flags.DUMP_CODE, lp);
            Logger.info("code dumped to file: " + Flags.DUMP_CODE);
          } catch (IOException e) {
            Logger.warning("could not dump code to file: " + Flags.DUMP_CODE);
          }
        }
        // static data
        if (Flags.DUMP_DATA != null) {
          try {
            Dump.dumpData(Flags.DUMP_DATA, lp);
            Logger.info("static data dumped to file: " + Flags.DUMP_DATA);
          } catch (IOException e) {
            Logger.warning("could not dump static data to file: " + Flags.DUMP_DATA);
          }
        }
        VSim.exit(0);
      }
      return lp;
    } else if (start != null) {
      throw new LinkerException("global start label '" + Flags.START + "' not defined in .text segment");
    } else {
      throw new LinkerException("global start label '" + Flags.START + "' not defined");
    }
  }

}
