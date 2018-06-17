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

  protected static Segment segment = Segment.TEXT;
  public static Program program = null;
  public static DebugInfo debug = null;

  protected static boolean inTextSegment() {
    return Assembler.segment == Segment.TEXT;
  }

  protected static boolean inDataSegment() {
    return Assembler.segment == Segment.DATA;
  }

  protected static boolean inRodataSegment() {
    return Assembler.segment == Segment.RODATA;
  }

  protected static boolean inBssSegment() {
    return Assembler.segment == Segment.BSS;
  }

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

  public static void error(String msg) {
    Assembler.error(msg, true);
  }

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

  private static void firstPass(ArrayList<Program> programs) {
    // resolve globals
    Assembler.handleGlobals(programs);
    // try to eval all statements and collect errors if any
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
          Globals.error("assembler: file '" + file + "' not found");
        } catch (IOException e) {
          Globals.error("assembler: file '" + file + "' could not be read");
        }
      }
    }
    // do first pass
    Assembler.firstPass(programs);
    // garbage collection
    System.gc();
    // report errors
    Message.errors();
    // return all processed programs, now linking
    return programs;
  }

}
