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


/**
 * The Assembler class contains useful methods to assemble RISC-V source files.
 */
public final class Assembler {

  /** current assembler segment */
  public static Segment segment = Segment.TEXT;
  /** current assembler program */
  public static Program program = null;
  /** current assembler debug info */
  public static DebugInfo debug = null;

  /**
   * This method is used to create a pretty formatted error and
   * adds this error to the error list {@link vsim.Globals#errors}.
   *
   * @param msg the error message
   * @param showSource true if you want to show the source line
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
   * This method is an alias for {@link Assembler#error} that sets to
   * true the parameter {@code showSource}.
   *
   * @param msg the error message
   * @see vsim.assembler.Assembler#error
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
   * This method handles all the global symbols of each program and
   * adds every symbol if possible to the global symbol table. This
   * method is part of the first pass of the assembler.
   *
   * @param programs all the assembled programs
   * @see vsim.Globals#globl
   */
  private static void handleGlobals(ArrayList<Program> programs) {
    for (Program program: programs) {
      // set current assembler program (useful for error report)
      Assembler.program = program;
      // add program ST to globals
      SymbolTable table = program.getST();
      String filename = program.getFilename();
      Globals.local.put(filename, table);
      // check globals of program
      for (String global: program.getGlobals()) {
        Symbol sym = table.getSymbol(global);
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
      for (Statement stmt: program.getStatements()) {
        // set current debug info
        Assembler.debug = stmt.getDebugInfo();
        stmt.resolve();
      }
    }
  }

  /**
   * This method is used to assemble all the RISC-V files and it is
   * called before the linkage process.
   *
   * @param files the RISC-V files to assemble
   * @see vsim.assembler.Program
   * @return all the assembled files
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
              Assembler.debug = new DebugInfo(lineno, line.trim(), file);
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
