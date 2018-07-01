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
import vsim.Settings;
import vsim.utils.Data;
import java.util.HashMap;
import java.util.ArrayList;
import vsim.assembler.Program;
import vsim.assembler.Segment;
import vsim.riscv.MemorySegments;
import vsim.assembler.statements.Statement;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The Linker class contains useful methods to generate a RISC-V linked program.
 */
public final class Linker {

  /** where static data segment begins */
  private static int dataAddress = MemorySegments.DATA_SEGMENT;
  /** where text segment starts */
  private static int textAddress = MemorySegments.TEXT_SEGMENT;

  /**
   * This method takes an array of RISC-V programs and stores
   * all the read-only segment data of these programs in memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkRodata(ArrayList<Program> programs) {
    int startAddress = Linker.dataAddress;
    for (Program program: programs) {
      program.setRodataStart(Linker.dataAddress);
      // store every byte of rodata of the current program
      for (Byte b: program.getRodata())
        Globals.memory.storeByte(Linker.dataAddress++, b);
      // align to a word boundary for next program if necessary
      if (Linker.dataAddress != startAddress) {
        Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
        startAddress = Linker.dataAddress;
      }
    }
  }

  /**
   * This method takes an array of RISC-V programs and stores
   * all the bss segment data of these programs in memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkBss(ArrayList<Program> programs) {
    int startAddress = Linker.dataAddress;
    for (Program program: programs) {
      program.setBssStart(Linker.dataAddress);
      for (Byte b: program.getBss())
        Globals.memory.storeByte(Linker.dataAddress++, b);
      if (Linker.dataAddress != startAddress) {
        Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
        startAddress = Linker.dataAddress;
      }
    }
  }

  /**
   * This method takes an array of RISC-V programs and stores
   * all the data segment data of these programs in memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkData(ArrayList<Program> programs) {
    int startAddress = Linker.dataAddress;
    for (Program program: programs) {
      program.setDataStart(Linker.dataAddress);
      for (Byte b: program.getData())
        Globals.memory.storeByte(Linker.dataAddress++, b);
      if (Linker.dataAddress != startAddress) {
        Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
        startAddress = Linker.dataAddress;
      }
    }
  }

  /**
   * This methods relocates all the symbols of all programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkSymbols(ArrayList<Program> programs) {
    for (Program program: programs) {
      program.setTextStart(Linker.textAddress);
      program.relocateSymbols();
      Linker.textAddress += program.getTextSize();
    }
  }

  /**
   * This method tries to build all statements of all programs,
   * i.e generates machine code.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  private static LinkedProgram linkPrograms(ArrayList<Program> programs) {
    // set start of text segment
    Linker.textAddress = MemorySegments.TEXT_SEGMENT;
    HashMap<Integer, Statement> all = new HashMap<Integer, Statement>();
    if (Globals.globl.get(Settings.START) != null &&
        Globals.globl.getSymbol(Settings.START).getSegment() == Segment.TEXT) {
      for (Program program: programs) {
        for (Statement stmt: program.getStatements()) {
          // build machine code
          stmt.build(Linker.textAddress);
          // store result in text segment
          int code = stmt.result().get(InstructionField.ALL);
          Globals.memory.storeWord(Linker.textAddress, code);
          // add this statement
          all.put(Linker.textAddress, stmt);
          // next word align address
          Linker.textAddress += Instruction.LENGTH;
        }
      }
    } else
      Errors.add("linker: no global start label: '" + Settings.START + "' set");
    return new LinkedProgram(all);
  }

  /**
   * This method tries to link all programs, handling all data, relocating
   * all symbols and reporting errors if any.
   *
   * @param programs an array of programs
   * @see vsim.linker.LinkedProgram
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  public static LinkedProgram link(ArrayList<Program> programs) {
    // reset this
    Linker.dataAddress = MemorySegments.DATA_SEGMENT;
    Linker.textAddress = MemorySegments.TEXT_SEGMENT;
    // handle static data
    Linker.linkRodata(programs);
    Linker.linkBss(programs);
    Linker.linkData(programs);
    Linker.linkSymbols(programs);
    // link all statements and get linked program
    LinkedProgram program = Linker.linkPrograms(programs);
    // report errors
    Errors.report();
    // clean all
    programs = null;
    System.gc();
    // return linked program, now simulate ?
    return program;
  }

}
