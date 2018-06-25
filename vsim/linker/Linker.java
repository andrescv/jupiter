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

import vsim.Globals;
import vsim.Settings;
import vsim.utils.Data;
import vsim.utils.Message;
import java.util.ArrayList;
import java.util.Hashtable;
import vsim.assembler.Program;
import vsim.assembler.Assembler;
import vsim.riscv.MemorySegments;
import vsim.assembler.statements.Statement;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


/**
 * The Linker class contains useful methods to generate a RISC-V linked program.
 */
public final class Linker {

  private Linker() { /* NOTHING */ }

  /** where static data segment begins */
  private static int dataAddress = MemorySegments.DATA_SEGMENT;
  /** where text segment starts */
  private static int textAddress = MemorySegments.TEXT_SEGMENT;

  /**
   * This method resets the Linker class static fields.
   */
  private static void reset() {
    Linker.dataAddress = MemorySegments.DATA_SEGMENT;
    Linker.textAddress = MemorySegments.TEXT_SEGMENT;
  }

  /**
   * This method takes an array of RISC-V programs and stores
   * all the read-only segment data of these programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void handleRodata(ArrayList<Program> programs) {
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
   * all the bss segment data of these programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void handleBss(ArrayList<Program> programs) {
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
   * all the data segment data of these programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void handleData(ArrayList<Program> programs) {
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
  private static void handleSymbols(ArrayList<Program> programs) {
    for (Program program: programs) {
      program.setTextStart(Linker.textAddress);
      program.relocateSymbols();
      Linker.textAddress += program.getTextSize();
    }
  }

  /**
   * This method tries to build all statements of all programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  private static LinkedProgram build(ArrayList<Program> programs) {
    // reset text address
    Linker.reset();
    Hashtable<Integer, Statement> all = new Hashtable<Integer, Statement>();
    if (Globals.globl.get(Settings.START) != null) {
      for (Program program: programs) {
        // set current program
        Assembler.program = program;
        for (Statement stmt: program.getStatements()) {
          // set current debug info
          Assembler.debug = stmt.getDebugInfo();
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
      Globals.error("no global start label: '" + Settings.START + "' set" + System.getProperty("line.separator"));
    return new LinkedProgram(all);
  }

  /**
   * This method tries to link all programs, handling all data and relocating
   * all symbols and reports errors if any.
   *
   * @param programs an array of programs
   * @see vsim.linker.LinkedProgram
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  public static LinkedProgram link(ArrayList<Program> programs) {
    Linker.reset();
    // handle static data
    Linker.handleRodata(programs);
    Linker.handleBss(programs);
    Linker.handleData(programs);
    Linker.handleSymbols(programs);
    // build all statements
    LinkedProgram program = Linker.build(programs);
    // report errors
    Message.errors();
    // clean all
    programs = null;
    System.gc();
    // return linked program, now simulate ?
    return program;
  }

}
