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


public final class Linker {

  private static int dataAddress = MemorySegments.DATA_SEGMENT;
  private static int textAddress = MemorySegments.TEXT_SEGMENT;

  private static void reset() {
    Linker.dataAddress = MemorySegments.DATA_SEGMENT;
    Linker.textAddress = MemorySegments.TEXT_SEGMENT;
  }

  private static void handleRodata(ArrayList<Program> programs) {
    int startAddress = Linker.dataAddress;
    for (Program program: programs) {
      program.setRodataStart(Linker.dataAddress);
      for (Byte b: program.getRodata())
        Globals.memory.storeByte(Linker.dataAddress++, b);
      if (Linker.dataAddress != startAddress) {
        Linker.dataAddress = Data.alignToWordBoundary(Linker.dataAddress);
        startAddress = Linker.dataAddress;
      }
    }
  }

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

  private static void handleSymbols(ArrayList<Program> programs) {
    for (Program program: programs) {
      program.setTextStart(Linker.textAddress);
      program.relocateSymbols();
      Linker.textAddress += program.getTextSize();
    }
  }

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
          stmt.build(Linker.textAddress, program.getFilename());
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
