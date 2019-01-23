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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import vsim.Errors;
import vsim.Globals;
import vsim.Log;
import vsim.Settings;
import vsim.assembler.DebugInfo;
import vsim.assembler.Program;
import vsim.assembler.Segment;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.UType;
import vsim.riscv.MemorySegments;
import vsim.riscv.instructions.InstructionField;
import vsim.utils.Data;
import vsim.utils.Message;


/**
 * The Linker class contains useful methods to generate a RISC-V linked program.
 */
public final class Linker {

  /** where static data segment begins */
  private static int dataAddress = MemorySegments.STATIC_SEGMENT;
  /** where text segment starts */
  private static int textAddress = MemorySegments.TEXT_SEGMENT_BEGIN;

  /**
   * This method takes an array of RISC-V programs and stores all the read-only segment data of these programs in
   * memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkRodata(ArrayList<Program> programs) {
    Log.info("saving .rodata in memory...");
    int startAddress = Linker.dataAddress;
    MemorySegments.RODATA_SEGMENT_BEGIN = Linker.dataAddress;
    MemorySegments.RODATA_SEGMENT_END = Linker.dataAddress;
    for (Program program : programs) {
      program.setRodataStart(Linker.dataAddress);
      // store every byte of rodata of the current program
      for (Byte b : program.getRodata())
        Globals.memory.privStoreByte(Linker.dataAddress++, b);
      // align to a word boundary for next program
      Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
      MemorySegments.RODATA_SEGMENT_END = Linker.dataAddress;
    }
    // move next address by 1 word to set a rodata address range properly
    if (Linker.dataAddress != startAddress)
      Linker.dataAddress += Data.WORD_LENGTH;
    else {
      Log.info("no .rodata data found");
      MemorySegments.RODATA_SEGMENT_BEGIN = -1;
      MemorySegments.RODATA_SEGMENT_END = -1;
    }
  }

  /**
   * This method takes an array of RISC-V programs and stores all the bss segment data of these programs in memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkBss(ArrayList<Program> programs) {
    Log.info("saving .bss data in memory...");
    int startAddress = Linker.dataAddress;
    for (Program program : programs) {
      program.setBssStart(Linker.dataAddress);
      // store every byte of bss of the current program
      for (Byte b : program.getBss())
        Globals.memory.privStoreByte(Linker.dataAddress++, b);
      // align to a word boundary for next program
      Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
    }
    // move next address by 1 word to set a bss address range properly
    if (Linker.dataAddress != startAddress)
      Linker.dataAddress += Data.WORD_LENGTH;
    else
      Log.info("no .bss data found");
  }

  /**
   * This method takes an array of RISC-V programs and stores all the data segment data of these programs in memory.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkData(ArrayList<Program> programs) {
    Log.info("saving .data in memory...");
    int startAddress = Linker.dataAddress;
    for (Program program : programs) {
      program.setDataStart(Linker.dataAddress);
      // store every byte of data of the current program
      for (Byte b : program.getData())
        Globals.memory.privStoreByte(Linker.dataAddress++, b);
      // align to a word boundary for next program
      Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
    }
    // move next address by 1 word to set a data address range properly
    if (Linker.dataAddress != startAddress)
      Linker.dataAddress += Data.WORD_LENGTH;
    else
      Log.info("no .data data found");
    // save heap segment start address
    Log.info("saving heap pointer");
    MemorySegments.HEAP_SEGMENT = Linker.dataAddress;
    MemorySegments.HEAP_SEGMENT_BEGIN = MemorySegments.HEAP_SEGMENT;
  }

  /**
   * This methods relocates all the symbols of all programs.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   */
  private static void linkSymbols(ArrayList<Program> programs) {
    Log.info("linking symbols...");
    // first relocate symbols
    for (Program program : programs) {
      program.setTextStart(Linker.textAddress);
      program.relocateSymbols();
      Linker.textAddress += program.getTextSize();
    }
    // then store references to this symbols
    for (Program program : programs)
      program.storeRefs();
  }

  /**
   * This method tries to build all statements of all programs, i.e generates machine code.
   *
   * @param programs an array of programs
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  private static LinkedProgram linkPrograms(ArrayList<Program> programs) {
    Log.info("linking programs...");
    // set start of text segment
    Linker.textAddress = MemorySegments.TEXT_SEGMENT_BEGIN;
    HashMap<Integer, Statement> all = new HashMap<Integer, Statement>();
    if (Globals.globl.get(Settings.START) != null
        && Globals.globl.getSymbol(Settings.START).getSegment() == Segment.TEXT) {
      Log.info("creating call <start> initial instructions");
      // far call to start label always the first (two) statements
      DebugInfo debug = new DebugInfo(0, 0, "call " + Settings.START, "start");
      // utype statement (CALL start)
      UType u = new UType("auipc", debug, "x6", new Relocation(Relocation.PCRELHI, Settings.START, debug));
      u.build(Linker.textAddress);
      Globals.memory.privStoreWord(Linker.textAddress, u.result().get(InstructionField.ALL));
      all.put(Linker.textAddress, u);
      // next word align address
      Linker.textAddress += Data.WORD_LENGTH;
      // itype statement (CALL start)
      IType i = new IType("jalr", debug, "x1", "x6", new Relocation(Relocation.PCRELLO, Settings.START, debug));
      i.build(Linker.textAddress);
      Globals.memory.privStoreWord(Linker.textAddress, i.result().get(InstructionField.ALL));
      all.put(Linker.textAddress, i);
      // next word align address
      Linker.textAddress += Data.WORD_LENGTH;
      for (Program program : programs) {
        Log.info("saving " + program.getFilename() + " instructions in memory...");
        for (Statement stmt : program.getStatements()) {
          // build machine code
          stmt.build(Linker.textAddress);
          // store result in text segment
          int code = stmt.result().get(InstructionField.ALL);
          Globals.memory.privStoreWord(Linker.textAddress, code);
          // add this statement
          all.put(Linker.textAddress, stmt);
          // next word align address
          Linker.textAddress += Data.WORD_LENGTH;
          if (Linker.textAddress > MemorySegments.TEXT_SEGMENT_END)
            Errors.add("linker: program to large > ~256MiB");
        }
      }
    } else
      Errors.add("linker: global start label '" + Settings.START + "' wast not found in text segment");
    return new LinkedProgram(all);
  }

  /**
   * This method tries to link all programs, handling all data, relocating all symbols and reporting errors if any.
   *
   * @param programs an array of programs
   * @see vsim.linker.LinkedProgram
   * @see vsim.assembler.Program
   * @return a RISC-V linked program
   */
  public static LinkedProgram link(ArrayList<Program> programs) {
    if (programs != null) {
      // reset this
      Linker.dataAddress = MemorySegments.STATIC_SEGMENT;
      // 2 words added because of the two initial statements representing the far call to START label
      Linker.textAddress = MemorySegments.TEXT_SEGMENT_BEGIN + 2 * Data.WORD_LENGTH;
      // handle static data
      Linker.linkRodata(programs);
      Linker.linkBss(programs);
      Linker.linkData(programs);
      Linker.linkSymbols(programs);
      // link all statements and get linked program
      LinkedProgram program = Linker.linkPrograms(programs);
      // report errors
      if (!Errors.report()) {
        // dump code statements ?
        if (Settings.CODE != null) {
          File file = new File(Settings.CODE);
          Linker.dumpCode(program, file);
          Settings.CODE = null;
        }
        // dump static data ?
        if (Settings.DATA != null) {
          File file = new File(Settings.DATA);
          Linker.dumpData(file);
          Settings.DATA = null;
        }
        // return linked program, now simulate ?
        return program;
      }
      return null;
    }
    return null;
  }

  /**
   * Dumps generated machine code to a file.
   *
   * @param program linked program
   * @param file file to dump machine
   */
  public static void dumpCode(LinkedProgram program, File file) {
    try {
      Log.info("dumping .text segment to file " + Settings.CODE);
      // create file if necessary
      if (!file.exists())
        file.createNewFile();
      // use info statements to dump machine code
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        for (InfoStatement stmt : program.getInfoStatements()) {
          bw.write(stmt.getMachineCode().substring(2));
          bw.newLine();
        }
        bw.close();
        Message.log("machine code dumped to: " + file + System.getProperty("line.separator"));
      } catch (IOException e) {
        Message.warning("the file " + file + " could not be written");
        Log.warning("the file " + file + " could not be written");
        Log.warning(e);
      }
    } catch (IOException e) {
      Message.warning("the file " + file + " could not be created");
      Log.warning("the file " + file + " could not be created");
      Log.warning(e);
    }
  }

  /**
   * Dumps static data to a file.
   *
   * @param file file to dump static data
   */
  public static void dumpData(File file) {
    try {
      Log.info("dumping static data segment to file " + Settings.DATA);
      // create file if necessary
      if (!file.exists())
        file.createNewFile();
      // use info statements to dump machine code
      try {
        BufferedWriter bw = new BufferedWriter(new FileWriter(file));
        long start = Integer.toUnsignedLong(MemorySegments.STATIC_SEGMENT);
        long end = Integer.toUnsignedLong(MemorySegments.HEAP_SEGMENT_BEGIN);
        for (long addr = start; addr < end; addr += Data.WORD_LENGTH) {
          bw.write(String.format("%08x", Globals.memory.privLoadWord((int) addr)));
          bw.newLine();
        }
        bw.close();
        Message.log("static data dumped to: " + file + System.getProperty("line.separator"));
      } catch (IOException e) {
        Message.warning("the file " + file + " could not be written");
        Log.warning("the file " + file + " could not be written");
        Log.warning(e);
      }
    } catch (IOException e) {
      Message.warning("the file " + file + " could not be created");
      Log.warning("the file " + file + " could not be created");
      Log.warning(e);
    }
  }

}
