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

package vsim.assembler;

import java.io.File;
import java.util.ArrayList;
import vsim.Errors;
import vsim.Globals;
import vsim.assembler.statements.Statement;


/**
 * The Assembler class assembles RISC-V source files.
 */
public final class Assembler {

  /** current assembler segment */
  public static Segment segment = Segment.TEXT;

  /** current assembler program */
  public static Program program = null;

  /** current assembler filename */
  public static String filename = null;

  /**
   * This method is used to assemble all the RISC-V files and it is called before the linkage process.
   *
   * @param files the RISC-V files to assemble
   * @see vsim.assembler.Program
   * @return all the assembled files
   */
  public static ArrayList<Program> assemble(ArrayList<File> files) {
    ArrayList<Program> programs = new ArrayList<Program>();
    // assemble all files
    if (files.size() > 0) {
      for (File file : files) {
        // ignore empty files
        if (file.length() == 0)
          continue;
        // current filename
        Assembler.filename = file.getAbsolutePath();
        // start in text segment
        Assembler.segment = Segment.TEXT;
        // create a new RISC-V Program
        Program program = new Program(Assembler.filename);
        // set current program
        Assembler.program = program;
        // parse line by line all file
        Parser.parse(file);
        // add this processed program only if it has statements
        if (program.getTextSize() > 0 || program.getDataSize() > 0)
          programs.add(program);
      }
    }
    // report syntax errors
    if (Errors.report()) {
      // clean all
      Assembler.program = null;
      Assembler.filename = null;
      Assembler.segment = Segment.TEXT;
      programs.trimToSize();
      // return all processed programs, now linking ?
      return null;
    }
    // do first pass
    for (Program program : programs) {
      // add program ST to Globals.local
      SymbolTable table = program.getST();
      String filename = program.getFilename();
      Globals.local.put(filename, table);
      // check globals of program
      for (String global : program.getGlobals()) {
        Symbol sym = table.getSymbol(global);
        DebugInfo debug = program.getGlobalDebug(global);
        if (sym != null) {
          if (!Globals.globl.add(global, sym))
            Errors.add(debug, "assembler", "'" + global + "' already defined as global in a different file");
        } else
          Errors.add(debug, "assembler", "'" + global + "' declared global label but not defined");
      }
    }
    // try to resolve all statements and collect errors if any
    for (Program program : programs) {
      for (Statement stmt : program.getStatements())
        stmt.resolve();
    }
    // no unlinked programs ?
    if (programs.size() == 0)
      Errors.add("assembler: no valid RISC-V source file was passed");
    // report errors
    if (!Errors.report()) {
      // clean all
      Assembler.program = null;
      Assembler.filename = null;
      Assembler.segment = Segment.TEXT;
      programs.trimToSize();
      // return all processed programs, now linking ?
      return programs;
    }
    return null;
  }

}
