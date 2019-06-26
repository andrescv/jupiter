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

package vsim.asm;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;

import vsim.Flags;
import vsim.Globals;
import vsim.Logger;
import vsim.asm.stmts.*;
import vsim.exceptions.*;
import vsim.utils.Data;
import vsim.utils.FS;


/** RISC-V unlinked program. */
public final class Program {

  /** program file path */
  private final Path file;

  /** program current segment */
  private Segment segment;

  /** program global symbols */
  private final HashMap<String, Integer> globals;
  /** program data word references (e.g .word ID)*/
  private final HashMap<Integer, Relocation> dataRefs;
  /** program rodata word references (e.g. .word ID)*/
  private final HashMap<Integer, Relocation> rodataRefs;

  /** program local symbol table */
  private final SymbolTable local;

  /** .bss start address */
  private int bssStart;
  /** .text start address */
  private int textStart;
  /** .data start address */
  private int dataStart;
  /** .rodata start address */
  private int rodataStart;

  /** .bss current index */
  private int bssIndex;
  /** .text current index */
  private int textIndex;
  /** .data current index */
  private int dataIndex;
  /** .rodata current index */
  private int rodataIndex;

  /** bytes stored in .bss */
  private final ArrayList<Byte> bss;
  /** bytes stored in .data */
  private final ArrayList<Byte> data;
  /** bytes stored in .rodata */
  private final ArrayList<Byte> rodata;
  /** instructions in .text */
  private final ArrayList<Statement> text;
  /** list of errors */
  private final ArrayList<String> errors;

  /**
   * Creates a new RISC-V unlinked program.
   *
   * @param program file path
   */
  protected Program(Path file) {
    this.file = file;
    // initial values
    segment = Segment.TEXT;
    textStart = rodataStart = bssStart = dataStart = 0;
    textIndex = rodataIndex = bssIndex = dataIndex = 0;
    // init lists
    bss = new ArrayList<>();
    data = new ArrayList<>();
    rodata = new ArrayList<>();
    text = new ArrayList<>();
    errors = new ArrayList<>();
    // init maps
    globals = new HashMap<>();
    dataRefs = new HashMap<>();
    rodataRefs = new HashMap<>();
    // init symbol table
    local = new SymbolTable();
  }

  /**
   * Parses program. This method should be called first.
   *
   * @throws AssemblerException if any syntax error occurs.
   */
  /** Parses program. */
  protected void parse() throws AssemblerException {
    try {
      (new Parser(new Lexer(new FileReader(file.toFile())), this)).parse();
    } catch (FileNotFoundException e) {
      addError("file not found: '" + file + "'");
    } catch (IOException e) {
      addError("file '" + file + "' could not be read");
    } catch (Exception e) {
      addError("unexpected exception occurred while parsing file: '" + file + "'");
    }
    report();
  }

  /**
   * Checks symbols. This should be called after calling parse method.
   *
   * @throws AssemblerException if an error occurs while checking symbols
   */
  protected void check() throws AssemblerException {
    Globals.local.put(file, local);
    for (String global : globals.keySet()) {
      Symbol sym = local.getSymbol(global);
      if (sym != null && !Globals.globl.contains(global)) {
        Globals.globl.add(global, sym);
      } else if (sym != null) {
        int line = globals.get(global);
        addError("'" + global + "' already defined as global in a different file", line, global);
      } else {
        int line = globals.get(global);
        addError("'" + global + "' declared global but not defined", line, global);
      }
    }
    // check relocation expansion
    for (Statement stmt : text) {
      try {
        stmt.check();
      } catch (RelocationException e) {
        addError(e.getMessage(), stmt.getLine(), e.getTarget());
      }
    }
    report();
  }

  /**
   * Generates machine code. This method should be called after calling check method.
   *
   * @throws AssemblerException if an error occurs while generating machine code
   */
  protected void build() throws AssemblerException {
    // first reassign locals
    for (String label : local.labels()) {
      Symbol sym = local.getSymbol(label);
      switch (sym.getSegment()) {
        case TEXT:
          sym.setAddress(sym.getAddress() + textStart);
          break;
        case RODATA:
          sym.setAddress(sym.getAddress() + rodataStart);
          break;
        case BSS:
          sym.setAddress(sym.getAddress() + bssStart);
          break;
        default:
          sym.setAddress(sym.getAddress() + dataStart);
          break;
      }
    }
    // then reassign globals
    for (String global : globals.keySet()) {
      Globals.globl.getSymbol(global).setAddress(local.getSymbol(global).getAddress());
    }
    // add data refs
    for (Integer index : dataRefs.keySet()) {
      Relocation ref = dataRefs.get(index);
      try {
        int address = ref.resolve(0);
        for (int i = 0; i < Data.WORD_LENGTH; i++) {
          byte b = (byte) ((address >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK);
          data.set(index, b);
        }
      } catch (Exception e) { }
    }
    // add rodata refs
    for (Integer index : rodataRefs.keySet()) {
      Relocation ref = rodataRefs.get(index);
      try {
        int address = ref.resolve(0);
        for (int i = 0; i < Data.WORD_LENGTH; i++) {
          byte b = (byte) ((address >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK);
          rodata.set(index, b);
        }
      } catch (Exception e) { }
    }
    // generate machine code
    int pc = textStart;
    for (Statement stmt : text) {
      try {
        stmt.build(pc);
        pc += Data.WORD_LENGTH;
      } catch (RegisterException e) {
        addError(e.getMessage(), stmt.getLine(), e.getRegister());
      } catch (AssemblerException e) {
        addError(e.getMessage(), stmt.getLine());
      }
    }
    report();
  }

  /**
   * Sets .text segment start address.
   *
   * @param start segment start address
   */
  protected void setTextStart(int start) {
    textStart = start;
  }

  /**
   * Sets .rodata segment start address.
   *
   * @param start segment start address
   */
  protected void setRodataStart(int start) {
    rodataStart = start;
  }

  /**
   * Sets .bss segment start address.
   *
   * @param start segment start address
   */
  protected void setBssStart(int start) {
    bssStart = start;
  }

  /**
   * Sets .data segment start address.
   *
   * @param start segment start address
   */
  protected void setDataStart(int start) {
    dataStart = start;
  }

  /**
   * Returns program .text segment.
   *
   * @return program .text segment
   */
  public ArrayList<Statement> text() {
    return text;
  }

  /**
   * Returns program .rodata segment.
   *
   * @return program .rodata segment
   */
  public ArrayList<Byte> rodata() {
    return rodata;
  }

  /**
   * Returns program .bss segment.
   *
   * @return program .bss segment
   */
  public ArrayList<Byte> bss() {
    return bss;
  }

  /**
   * Returns program .data segment.
   *
   * @return program .data segment
   */
  public ArrayList<Byte> data() {
    return data;
  }

  // Parser Utilities

  /**
   * Verifies if the current segment is .text
   *
   * @return true if the current segment is .text, false if not
   */
  protected boolean inText() {
    return segment == Segment.TEXT;
  }

  /**
   * Verifies if the current segment is .data
   *
   * @return true if the current segment is .data, false if not
   */
  protected boolean inData() {
    return segment == Segment.DATA;
  }

  /**
   * Verifies if the current segment is .rodata
   *
   * @return true if the current segment is .rodata, false if not
   */
  protected boolean inRodata() {
    return segment == Segment.RODATA;
  }

  /**
   * Verifies if the current segment is .bss
   *
   * @return true if the current segment is .bss, false if not
   */
  protected boolean inBss() {
    return segment == Segment.BSS;
  }

  /**
   * Adds a symbol in the local symbol table.
   *
   * @param label symbol name
   * @param line line number
   * @param col column
   */
  protected void emitSymbol(String label, int line, int col) {
    label = label.substring(0, label.length() - 1);
    if (!local.contains(label)) {
      switch (segment) {
        case DATA:
          local.add(label, segment, dataIndex);
          break;
        case RODATA:
          local.add(label, segment, rodataIndex);
          break;
        case BSS:
          local.add(label, segment, bssIndex);
          break;
        default:
          local.add(label, segment, textIndex);
          break;
      }
    } else {
      addError("label '" + label + "' is already defined", line, col);
    }
  }

  /**
   * Adds a new statement in .text segment.
   *
   * @param stmt statement
   */
  private void add(Statement stmt) {
    if (!inText()) {
      addError("instructions can appear only in text segment", stmt.getLine());
    } else {
      text.add(stmt);
      textIndex += Data.WORD_LENGTH;
    }
  }

  /**
   * Adds a new r4-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param rs3 register source 2
   */
  protected void addR4Type(int line, String mnemonic, int rd, int rs1, int rs2, int rs3) {
    add(new R4Type(file, line, mnemonic, rd, rs1, rs2, rs3));
  }

  /**
   * Adds a new r-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param rs2 register source 2
   */
  protected void addRType(int line, String mnemonic, int rd, int rs1, int rs2) {
    add(new RType(file, line, mnemonic, rd, rs1, rs2));
  }

  /**
   * Adds a new i-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param rs1 register source 1
   * @param imm immediate
   */
  protected void addIType(int line, String mnemonic, int rd, int rs1, Object imm, int relType) {
    add(new IType(file, line, mnemonic, rd, rs1, getImm(imm, relType)));
  }

  /**
   * Adds a new s-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param offset store offset
   */
  protected void addSType(int line, String mnemonic, int rs1, int rs2, Object offset, int relType) {
    add(new SType(file, line, mnemonic, rs1, rs2, getImm(offset, relType)));
  }

  /**
   * Adds a new b-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rs1 register source 1
   * @param rs2 register source 2
   * @param offset branch offset
   */
  protected void addBType(int line, String mnemonic, int rs1, int rs2, Object offset, int relType) {
    add(new BType(file, line, mnemonic, rs1, rs2, getImm(offset, relType)));
  }

  /**
   * Adds a new u-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param imm immediate
   */
  protected void addUType(int line, String mnemonic, int rd, Object imm, int relType) {
    add(new UType(file, line, mnemonic, rd, getImm(imm, relType)));
  }

  /**
   * Adds a new j-type statement in .text segment.
   *
   * @param line program line number
   * @param mnemonic statement mnemonic
   * @param rd destination register
   * @param offset jump offset
   */
  protected void addJType(int line, String mnemonic, int rd, Object offset, int relType) {
    add(new JType(file, line, mnemonic, rd, getImm(offset, relType)));
  }

  /** Handles .text directive */
  protected void emitText() {
    segment = Segment.TEXT;
  }

  /** Handles .rodata directive */
  protected void emitRodata() {
    segment = Segment.RODATA;
  }

  /** Handles .bss directive */
  protected void emitBss() {
    segment = Segment.BSS;
  }

  /** Sets .data as current segment */
  protected void emitData() {
    segment = Segment.DATA;
  }

  /**
   * Adds a byte value in the current data segment.
   *
   * @param b byte value
   */
  protected void addByte(byte b) {
    switch (segment) {
      case DATA:
        data.add(b);
        dataIndex++;
        break;
      case RODATA:
        rodata.add(b);
        rodataIndex++;
        break;
      case BSS:
        bss.add(b);
        bssIndex++;
        break;
      default:
        break;
    }
  }

  /**
   * Handles .byte directive.
   *
   * @param bytes list of bytes
   * @param line source line number
   * @param col source column number
   * @param cols args source column numbers
   */
  protected void emitBytes(ArrayList<Integer> bytes, int line, int col, ArrayList<Integer> cols) {
    if (inData() || inRodata()) {
      for (Integer b : bytes) {
        int c = cols.remove(0);
        if (!Data.validByte(b) && Flags.EXTRICT) {
          addError("lossy conversion to byte: " + b + " → " + (byte)(b & Data.BYTE_MASK), line, c);
        } else if (!Data.validByte(b)) {
          String msg = "lossy conversion to byte: " + b + " → " + (byte)(b & Data.BYTE_MASK);
          Logger.warning(String.format("assembler:%d:%d: %s", line, c, msg));
        }
        addByte((byte) (b & Data.BYTE_MASK));
      }
    } else {
      addError(".byte directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .half directive.
   *
   * @param halfs list of halfs
   * @param mode directive mode (e.g .half, .short)
   * @param line source line number
   * @param col source column number
   * @param cols args source column numbers
   */
  protected void emitHalfs(ArrayList<Integer> halfs, String mode, int line, int col, ArrayList<Integer> cols) {
    if (inData() || inRodata()) {
      for (Integer h : halfs) {
        int c = cols.remove(0);
        if (!Data.validHalf(h) && Flags.EXTRICT) {
          addError("lossy conversion to half: " + h + " → " + (short)(h & Data.HALF_MASK), line, c);
        } else if (!Data.validHalf(h)) {
          String msg = "lossy conversion to half: " + h + " → " + (short)(h & Data.BYTE_MASK);
          Logger.warning(String.format("assembler:%d:%d: %s", line, c, msg));
        }
        for (int i = 0; i < Data.HALF_LENGTH; i++) {
          byte b = (byte) ((h >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK);
          addByte(b);
        }
      }
    } else {
      addError(mode + " directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .word directive.
   *
   * @param words list of words
   * @param mode directive mode (e.g .word, .long)
   * @param line source line number
   * @param col source column number
   */
  protected void emitWords(ArrayList<Integer> words, String mode, int line, int col) {
    if (inData() || inRodata()) {
      for (Integer w : words) {
        for (int i = 0; i < Data.WORD_LENGTH; i++) {
          byte b = (byte) ((w >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK);
          addByte(b);
        }
      }
    } else {
      addError(mode + " directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .word ID directive.
   *
   * @param ids list of ids
   * @param mode directive mode (e.g .word, .long)
   * @param line source line number
   * @param col source column number
   */
  protected void emitRefs(ArrayList<String> ids, String mode, int line, int col) {
    if (inData() || inRodata()) {
      for (String id : ids) {
        if (inData()) {
          dataRefs.put(dataIndex, new Relocation(file, Relocation.DEFAULT, id));
        } else {
          rodataRefs.put(rodataIndex, new Relocation(file, Relocation.DEFAULT, id));
        }
        addByte((byte) 0);
        addByte((byte) 0);
        addByte((byte) 0);
        addByte((byte) 0);
      }
    } else {
      addError(mode + " directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .float directive.
   *
   * @param floats list of floats
   * @param line source line number
   * @param col source column number
   */
  protected void emitFloats(ArrayList<Float> floats, int line, int col) {
    if (inData() || inRodata()) {
      for (Float f : floats) {
        int w = Float.floatToRawIntBits(f);
        for (int i = 0; i < Data.WORD_LENGTH; i++) {
          byte b = (byte) ((w >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK);
          addByte(b);
        }
      }
    } else {
      addError(".float directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .asciiz directive.
   *
   * @param str string to store
   * @param mode directive mode (e.g .asciiz, .asciz, .string)
   * @param line source line number
   * @param col source column number
   */
  protected void emitAsciiz(String str, String mode, int line, int col) {
    emitAscii(str, true, mode, line, col);
  }

  /**
   * Handles .ascii directive.
   *
   * @param str string to store
   * @param line source line number
   * @param col source column number
   */
  protected void emitAscii(String str, int line, int col) {
    emitAscii(str, false, ".ascii", line, col);
  }

  /**
   * Handles .zero directive.
   *
   * @param bytes bytes to reserve
   * @param mode directive mode (e.g .space or .zero)
   * @param line source line number
   * @param col source column number
   */
  protected void emitZero(int bytes, String mode, int line, int col) {
    if (inData() || inBss()) {
      if (bytes > 0) {
        for (int i = 0; i < bytes; i++) {
          addByte((byte) 0);
        }
      } else {
        addError("invalid " + mode + " argument: " + bytes + ", expected a value > 0", line);
      }
    } else {
      addError(mode + " directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Handles .globl directive.
   *
   * @param sym symbol name
   * @param line source line number
   * @param col source column number
   */
  protected void emitGlobal(String sym, int line, int col) {
    if (!globals.containsKey(sym)) {
      globals.put(sym, line);
    } else {
      addError("label '" + sym + "' is already defined as global", line, col);
    }
  }

  /**
   * Handles .align directive.
   *
   * @param imm align value
   * @param line source line number
   * @param col source column number
   */
  protected void emitAlign(int imm, int line, int col) {
    balign((int) Math.pow(2, imm));
  }

  /**
   * Handles .balign directive.
   *
   * @param imm align value
   * @param line source line number
   * @param col source column number
   */
  protected void emitBAlign(int imm, int line, int col) {
    balign(imm);
  }

  /**
   * Stores string bytes in current segment.
   *
   * @param str string to store
   * @param nt add null terminator
   * @param mode directive mode (e.g ascii, asciiz, etc)
   * @param line source line number
   * @param col source column number
   */
  private void emitAscii(String str, boolean nt, String mode, int line, int col) {
    if (inData() || inRodata()) {
      for (int i = 0; i < str.length(); i++) {
        addByte((byte) str.charAt(i));
      }
      if (nt) {
        addByte((byte) 0);
      }
    } else {
      addError(mode + " directive can not appear in ." + segment.toString().toLowerCase() + " segment", line, col);
    }
  }

  /**
   * Aligns current segment to a byte boundary.
   *
   * @param n align value
   */
  private void balign(int n) {
    int padding = 0;
    switch (segment) {
      case DATA:
        padding = n - Integer.remainderUnsigned(dataIndex, n);
      case RODATA:
        padding = n - Integer.remainderUnsigned(rodataIndex, n);
      case BSS:
        padding = n - Integer.remainderUnsigned(bssIndex, n);
      default:
        break;
    }
    for (int i = 0; i < padding; i++)
      addByte((byte) 0);
  }

  /**
   * Returns an int value that represents the given immediate context.
   *
   * @param imm immediate context
   * @return int value that represents the immediate context.
   */
  private Object getImm(Object imm, int relType) {
    if (imm instanceof String) {
      return new Relocation(file, relType, (String) imm);
    }
    return (int) imm;
  }

  // Other utilities

  /**
   * Verifies if the program contains the main entry point (start label).
   *
   * @return {@code true} if the program contains the main entry point, false if not
   */
  protected boolean hasEntryPoint() {
    return globals.containsKey(Flags.START);
  }

  /** Adds a tail call to the main entry point. */
  protected void addTailCallToEntryPoint() {
    // add tail call
    text.add(0, new UType(null, -1, "auipc", 6, new Relocation(file, Relocation.PCRELHI, Flags.START)));
    text.add(1, new IType(null, -1, "jalr", 0, 6, new Relocation(file, Relocation.PCRELLO, Flags.START)));
    // correct .text symbols
    int offset = Data.WORD_LENGTH * 2;
    for (String label : local.labels()) {
      Symbol sym = local.getSymbol(label);
      if (sym.getSegment() == Segment.TEXT) {
        sym.setAddress(sym.getAddress() + offset);
      }
    }
    // update .text index
    textIndex += offset;
  }

  /**
   * Adds an error to the error list.
   *
   * @param msg error message
   */
  private void addError(String msg) {
    // don't add repeated errors
    if (!errors.contains(msg)) {
      errors.add(msg);
    }
  }

  /**
   * Adds an error to the error list.
   *
   * @param msg error message
   * @param line source line number
   * @param col source column number
   */
  protected void addError(String msg, int line, int col) {
    String sourceLine = FS.getLine(file, line);
    String error = String.format("asm::%d:%d: %s", line, col, msg);
    if (sourceLine != null) {
      sourceLine = sourceLine.replace("\t", " ");
      sourceLine = sourceLine.replaceAll("[;#].*", "").replaceAll("\\s+$", "");
      String pointer = "";
      for (int i = 0; i < col - 1; i++) {
        pointer += " ";
      }
      pointer += "^";
      error += Data.EOL + Data.EOL + " > " + sourceLine + Data.EOL + "   " + pointer;
    }
    addError(error);
  }

  /**
   * Adds an error to the error list.
   *
   * @param msg error message
   * @param line source line number
   */
  protected void addError(String msg, int line) {
    String sourceLine = FS.getLine(file, line);
    String error = String.format("asm::%d: %s", line, msg);
    if (sourceLine != null) {
      sourceLine = sourceLine.replace("\t", " ");
      sourceLine = sourceLine.replaceAll("[;#].*", "").replaceAll("\\s+$", "");
      error += Data.EOL + Data.EOL + " > " + sourceLine;
    }
    addError(error);
  }

  /**
   * Adds an error to the error list.
   *
   * @param msg error message
   * @param line source line number
   * @param token token to find
   */
  protected void addError(String msg, int line, String token) {
    String sourceLine = FS.getLine(file, line);
    if (sourceLine != null) {
      int col = sourceLine.indexOf(token) + 1;
      sourceLine = sourceLine.replace("\t", " ");
      sourceLine = sourceLine.replaceAll("[;#].*", "").replaceAll("\\s+$", "");
      String pointer = "";
      for (int i = 0; i < col - 1; i++) {
        pointer += " ";
      }
      pointer += "^";
      String error = String.format("asm::%d:%d: %s%s%s > %s", line, col, msg, Data.EOL, Data.EOL, sourceLine);
      addError(error + Data.EOL + "   " + pointer);
    } else {
      addError(String.format("asm::%d: %s", line, msg));
    }
  }

  /**
   * Reports preprocessor and assembler errors.
   *
   * @throws AssemblerException if {@code errors.size() != 0}
   */
  private void report() throws AssemblerException {
    if (errors.size() != 0) {
      for (String error : errors) {
        Logger.error(error + Data.EOL);
      }
      errors.clear();
      throw new AssemblerException("simulation halted due to assembly errors");
    }
  }

}
