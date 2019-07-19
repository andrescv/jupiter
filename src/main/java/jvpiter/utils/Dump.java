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

package jvpiter.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jvpiter.asm.stmts.Statement;
import jvpiter.linker.LinkedProgram;


/** Jvpiter dump utility. */
public final class Dump {

  /**
   * Dumps generated machine code.
   *
   * @param file destination file
   * @param program RISC-V linked program
   * @throws IOException if an I/O error occurs
   */
  public static void dumpCode(File file, LinkedProgram program) throws IOException {
    String out = "";
    for (Statement stmt : program.statements()) {
      out += String.format("0x%08x", stmt.code().bits()) + Data.EOL;
    }
    FS.write(file, out);
  }

  /**
   * Dumps static data.
   *
   * @param file destination file
   * @param program RISC-V linked program
   * @throws IOException if an I/O error occurs
   */
  public static void dumpData(File file, LinkedProgram program) throws IOException {
    String out = "";
    ArrayList<Byte> data = program.data();
    if (data.size() > 0) {
      int size = (data.size() / Data.WORD_LENGTH) * Data.WORD_LENGTH;
      for (int i = 0; i < size; i += Data.WORD_LENGTH) {
        String cell = "";
        for (int j = 0; j < Data.WORD_LENGTH; j++) {
          cell = String.format("%02x", data.get(i + j)) + cell;
        }
        out += "0x" + cell + Data.EOL;
      }
      String cell = "";
      for (int i = size; i < data.size(); i++) {
        cell = String.format("%02x", data.get(i)) + cell;
      }
      for (int i = 0; i < Data.offsetToWordAlign(data.size()); i++) {
        cell = "00" + cell;
      }
      if (!cell.equals("")) {
        out += "0x" + cell + Data.EOL;
      }
    }
    FS.write(file, out);
  }

}
