package vsim.linker;

import vsim.Globals;
import vsim.utils.Data;
import java.util.ArrayList;
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

  private static ArrayList<Statement> build(ArrayList<Program> programs) {
    // reset text address
    Linker.reset();
    ArrayList<Statement> all = new ArrayList<Statement>();
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
        Linker.textAddress += Instruction.LENGTH;
        // add this statement
        all.add(stmt);
      }
    }
    all.trimToSize();
    return all;
  }

  public static void link(ArrayList<Program> programs) {
    Linker.reset();
    Linker.handleRodata(programs);
    Linker.handleBss(programs);
    Linker.handleData(programs);
    Linker.handleSymbols(programs);
    // TODO
    Linker.build(programs);
  }

}
