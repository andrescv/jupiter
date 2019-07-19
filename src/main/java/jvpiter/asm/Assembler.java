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

package jvpiter.asm;

import java.io.File;
import java.util.ArrayList;

import jvpiter.Logger;
import jvpiter.exc.AssemblerException;
import jvpiter.utils.Data;


/** Jvpiter RISC-V assembler */
public final class Assembler {

  /**
   * Assembles RISC-V assembly files.
   *
   * @param files list of files to assemble
   * @return list of RISC-V unlinked programs
   * @throws AssemblerException if an error occurs during assembly phase
   */
  public static ArrayList<Program> assemble(ArrayList<File> files) throws AssemblerException {
    if (files != null && files.size() > 0) {
      ArrayList<Program> programs = new ArrayList<>(files.size());
      // parse files
      for (File file : files) {
        Program program = new Program(file);
        program.parse();
        programs.add(program);
      }
      // set program that contains start label at first position
      if (programs.size() > 1) {
        int index = -1;
        for (int i = 0; i < programs.size(); i++) {
          if (programs.get(i).hasEntryPoint()) {
            index = i;
            break;
          }
        }
        if (index != -1) {
          programs.add(0, programs.remove(index));
        }
      }
      // check symbols
      for (Program program : programs) {
        program.check();
      }
      // add tail call to start label
      if (programs.get(0).hasEntryPoint()) {
        programs.get(0).addTailCallToEntryPoint();
      }
      // assign start addresses
      int address = Data.TEXT;
      // set .text start
      for (Program program : programs) {
        program.setTextStart(address);
        address += program.text().size() * Data.WORD_LENGTH;
      }
      // set .rodata start
      for (Program program : programs) {
        program.setRodataStart(address);
        address += program.rodata().size();
        address = Data.alignToWordBoundary(address);
      }
      // set .bss start
      for (Program program : programs) {
        program.setBssStart(address);
        address += program.bss().size();
        address = Data.alignToWordBoundary(address);
      }
      // set .data start
      for (Program program : programs) {
        program.setDataStart(address);
        address += program.bss().size();
        address = Data.alignToWordBoundary(address);
      }
      // generate machine code
      for (Program program : programs) {
        program.build();
      }
      return programs;
    } else {
      Logger.error("no RISC-V files passed...");
      throw new AssemblerException("simulation halted due to assembly errors");
    }
  }

}
