package vsim.assembler;

import vsim.Globals;
import vsim.Settings;
import vsim.utils.Message;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;
import java.io.StringReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import vsim.assembler.pseudos.PSeudo;
import vsim.assembler.statements.Statement;


public final class Assembler {

  protected static int lineno = 0;
  protected static Program program = null;


  protected static void error(String msg) {
    String filename = Assembler.program.getFilename();
    Globals.errors.add(
      "assembler: " + filename + ": at line: " + Assembler.lineno + ": " + msg
    );
  }

  protected static void warning(String msg) {
    if (!Settings.QUIET) {
      String filename = Assembler.program.getFilename();
      Message.warning(
        "assembler: " + filename + ": at line: " + Assembler.lineno + ": " + msg
      );
    }
  }

  public static ArrayList<Program> assemble(ArrayList<String> files) {
    ArrayList<Program> programs = new ArrayList<Program>();
    // assemble all files
    if (files.size() > 0) {
      for (String file: files) {
        try {
          // create a new RISC-V Program
          Program program = new Program(file);
          // set current program
          Assembler.program = program;
          // parse line by line all file
          BufferedReader br = new BufferedReader(new FileReader(file));
          String line;
          // reset line count
          Assembler.lineno = 1;
          while ((line = br.readLine()) != null) {
            // remove comments
            if (line.indexOf(';') != -1)
              line = line.substring(0, line.indexOf(';'));
            if (line.indexOf('#') != -1)
              line = line.substring(0, line.indexOf('#'));
            // parse line only if != nothing
            if (!line.trim().equals(""))
              Parser.parse(line);
            // increment line count
            Assembler.lineno++;
          }
          // add this processed program
          programs.add(program);
        } catch (FileNotFoundException e) {
          Globals.errors.add("assembler: file '" + file + "' not found");
        } catch (IOException e) {
          Globals.errors.add("assembler: file '" + file + "' could not be read");
        }
      }
    }
    return programs;
  }

}
