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

package vsim.assembler;

import vsim.Globals;
import vsim.Settings;
import vsim.utils.Message;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import vsim.assembler.statements.Statement;


public final class Assembler {

  private Assembler() { /* NOTHING */ }

  /** current assembler segment */
  protected static Segment segment = Segment.TEXT;
  /** current assembler program */
  public static Program program = null;
  /** current assembler debug info */
  public static DebugInfo debug = null;

  /**
   * This method checks if the current assembler segment is the text segment.
   *
   * @see vsim.assembler.Segment
   * @return true if the text segment is the current segment, false otherwise
   */
  protected static boolean inTextSegment() {
    return Assembler.segment == Segment.TEXT;
  }

  /**
   * This method checks if the current assembler segment is the data segment.
   *
   * @see vsim.assembler.Segment
   * @return true if the data segment is the current segment, false otherwise
   */
  protected static boolean inDataSegment() {
    return Assembler.segment == Segment.DATA;
  }

  /**
   * This method checks if the current assembler segment is the rodata segment.
   *
   * @see vsim.assembler.Segment
   * @return true if the rodata segment is the current segment, false otherwise
   */
  protected static boolean inRodataSegment() {
    return Assembler.segment == Segment.RODATA;
  }

  /**
   * This method checks if the current assembler segment is the bss segment.
   *
   * @see vsim.assembler.Segment
   * @return true if the bss segment is the current segment, false otherwise
   */
  protected static boolean inBssSegment() {
    return Assembler.segment == Segment.BSS;
  }

  /**
   * This method checks is used to create a pretty formatted error and
   * adds this error to the error list {@link Globals#errros}.
   *
   * @param msg the error message
   * @param showSource true if you want to show the source line
   * @see Globals#errors
   */
  public static void error(String msg, boolean showSource) {
    String filename = Assembler.program.getFilename();
    String newline = System.getProperty("line.separator");
    String source = Assembler.debug.getSource();
    int lineno = Assembler.debug.getLineNumber();
    if (showSource)
      Globals.error(
        filename + ":" + "assembler:" + lineno + ": " + msg +
        newline + "    |" +
        newline + "    └─> " + source + newline
      );
    else
      Globals.error(
        filename + ":" + "assembler:" + lineno + ": " + msg + newline
      );
  }

  /**
   * This method is an alias for {@link Globals#error} that sets to
   * true the parameter showSource.
   *
   * @param msg the error message
   * @see Globals#error
   */
  public static void error(String msg) {
    Assembler.error(msg, true);
  }

  /**
   * This method is used to pretty print a warning.
   *
   * @param msg the warning message
   */
  public static void warning(String msg) {
    if (!Settings.QUIET) {
      String filename = Assembler.program.getFilename();
      String newline = System.getProperty("line.separator");
      String source = Assembler.debug.getSource();
      int lineno = Assembler.debug.getLineNumber();
      Message.warning(
        filename + ":" + "assembler:" + lineno + ": " + msg +
        newline + "    |" +
        newline + "    └─> " + source + newline
      );
    }
  }

  /**
   * This method handle all the global symbols of each program and
   * adds every symbol if possible to the global symbol table. This
   * method is part of the first pass of the assembler.
   *
   * @param programs all the assembled programs
   * @see Globals#globl
   */
  private static void handleGlobals(ArrayList<Program> programs) {
    for (Program program: programs) {
      // set current assembler program
      Assembler.program = program;
      // add program ST to globals
      SymbolTable table = program.getST();
      String filename = program.getFilename();
      Globals.local.put(filename, table);
      // check globals of program
      for (String global: program.getGlobals()) {
        Sym sym = table.getSymbol(global);
        if (sym != null) {
          if(!Globals.globl.add(global, sym))
            Assembler.error(
              "'" + global + "' already defined as global in a different file",
              false
            );
        } else
          Assembler.error(
            "'" + global + "' declared global label but not defined",
            false
          );
      }
    }
  }

  /**
   * This method is used to make the first pass of the assembler.
   *
   * @param programs assembled programs
   * @see Assembler#handleGlobals
   */
  private static void firstPass(ArrayList<Program> programs) {
    // resolve globals
    Assembler.handleGlobals(programs);
    // try to resolve all statements and collect errors if any
    for (Program program: programs) {
      // set current assembler program
      Assembler.program = program;
      String filename = program.getFilename();
      for (Statement stmt: program.getStatements()) {
        // set current debug info
        Assembler.debug = stmt.getDebugInfo();
        stmt.resolve(filename);
      }
    }
  }

  /**
   * This method is used to assemble all the RISC-V files and it is
   * called before the linkage process.
   *
   * @param files the RISC-V files to assemble
   */
  public static ArrayList<Program> assemble(ArrayList<String> files) {
    ArrayList<Program> programs = new ArrayList<Program>();
    // assemble all files
    if (files.size() > 0) {
      for (String file: files) {
        try {
          // start in text segment
          Assembler.segment = Segment.TEXT;
          // create a new RISC-V Program
          Program program = new Program(file);
          // set current program
          Assembler.program = program;
          // parse line by line all file
          BufferedReader br = new BufferedReader(new FileReader(file));
          String line;
          // reset line count
          int lineno = 1;
          while ((line = br.readLine()) != null) {
            // remove comments
            if (line.indexOf(';') != -1)
              line = line.substring(0, line.indexOf(';'));
            if (line.indexOf('#') != -1)
              line = line.substring(0, line.indexOf('#'));
            // parse line only if != nothing
            if (!line.trim().equals("")) {
              // set current debug
              Assembler.debug = new DebugInfo(lineno, line.trim());
              // parse line
              Parser.parse(line);
            }
            // increment line count
            lineno++;
          }
          // add this processed program
          programs.add(program);
        } catch (FileNotFoundException e) {
          Globals.error("assembler: file '" + file + "' not found" + System.getProperty("line.separator"));
        } catch (IOException e) {
          Globals.error("assembler: file '" + file + "' could not be read" + System.getProperty("line.separator"));
        }
      }
    }
    // do first pass
    Assembler.firstPass(programs);
    // report errors
    Message.errors();
    // clean all
    Assembler.program = null;
    Assembler.debug = null;
    System.gc();
    programs.trimToSize();
    // return all processed programs, now linking ?
    return programs;
  }

}
