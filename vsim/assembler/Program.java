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
import java.util.Set;
import vsim.utils.Data;
import java.util.HashMap;
import vsim.utils.Message;
import java.util.ArrayList;
import vsim.linker.Relocation;
import vsim.riscv.instructions.Instruction;
import vsim.assembler.statements.Statement;


/**
 * The class Program represents an unlinked program.
 */
public final class Program {

  /** filename of this unlinked program */
  private String filename;

  /** current text segment index */
  private int textIndex;
  /** start of text segment of this program*/
  private int textStart;
  /** array of statements of this program */
  private ArrayList<Statement> stmts;

  /** controls if the next datum needs aligned */
  private boolean align;
  /** alignment value */
  private int alignVal;

  /** global symbols declared in this program */
  private HashMap<String, DebugInfo> globals;
  /** local symbols declared in this program */
  private SymbolTable table;

  /** current data segment index */
  private int dataIndex;
  /** start of data segment of this program */
  private int dataStart;
  /** array of bytes that belongs to the data segment */
  private ArrayList<Byte> data;
  /** data segment symbols (e.g. .word LABEL) */
  private HashMap<Integer, Relocation> dataAddr;

  /** current rodata segment index */
  private int rodataIndex;
  /** start of rodata segment of this program */
  private int rodataStart;
  /** array of bytes that belongs to the rodata segment */
  private ArrayList<Byte> rodata;
  /** rodata segment symbols (e.g. .word LABEL) */
  private HashMap<Integer, Relocation> rodataAddr;

  /** current bss segment index */
  private int bssIndex;
  /** start of bss segment of this program */
  private int bssStart;
  /** array of bytes that belongs to the bss segment */
  private ArrayList<Byte> bss;

  /**
   * Unique constructor that initializes a new unlinked program.
   *
   * @param filename the filename of this program
   */
  protected Program(String filename) {
    // filename attached to this program
    this.filename = filename;
    // statements
    this.stmts = new ArrayList<Statement>();
    this.textIndex = 0;
    this.textStart = 0;
    // align
    this.align = false;
    this.alignVal = 0;
    // symbols
    this.table = new SymbolTable();
    this.globals = new HashMap<String, DebugInfo>();
    // data segment control
    this.dataIndex = 0;
    this.dataStart = 0;
    this.data = new ArrayList<Byte>();
    this.dataAddr = new HashMap<Integer, Relocation>();
    // rodata segment control
    this.rodataIndex = 0;
    this.rodataStart = 0;
    this.rodata = new ArrayList<Byte>();
    this.rodataAddr = new HashMap<Integer, Relocation>();
    // bss segment control
    this.bssIndex = 0;
    this.bssStart = 0;
    this.bss = new ArrayList<Byte>();
  }

  /**
   * This method is used to relocate all local and global symbols
   * of this program.
   */
  public void relocateSymbols() {
    // first locals
    for (String label: this.table.labels()) {
      Symbol sym = this.table.getSymbol(label);
      int offset = sym.getAddress();
      switch (sym.getSegment()) {
        case DATA:
          this.table.set(label, offset + this.dataStart);
          break;
        case TEXT:
          this.table.set(label, offset + this.textStart);
          break;
        case RODATA:
          this.table.set(label, offset + this.rodataStart);
          break;
        case BSS:
          this.table.set(label, offset + this.bssStart);
          break;
      }
    }
    // then globals
    for (String global: this.globals.keySet())
      Globals.globl.set(global, this.table.get(global));
  }


  /**
   * This method stores the references in the specific segments.
   */
  public void storeRefs() {
    // data segment
    for (Integer index: this.dataAddr.keySet()) {
      Relocation ref = this.dataAddr.get(index);
      Globals.memory.privStoreWord(index + this.dataStart, ref.getTargetAddress());
    }
    // rodata segment
    for (Integer index: this.rodataAddr.keySet()) {
      Relocation ref = this.rodataAddr.get(index);
      Globals.memory.privStoreWord(index + this.rodataStart, ref.getTargetAddress());
    }
  }

  /**
   * This method sets the text segment start address of this program.
   *
   * @param address the start address of the text segment
   */
  public void setTextStart(int address) {
    this.textStart = address;
  }

  /**
   * This method sets the data segment start address of this program.
   *
   * @param address the start address of the data segment
   */
  public void setDataStart(int address) {
    this.dataStart = address;
  }

  /**
   * This method sets the rodata segment start address of this program.
   *
   * @param address the start address of the rodata segment
   */
  public void setRodataStart(int address) {
    this.rodataStart = address;
  }

  /**
   * This method sets the bss segment start address of this program.
   *
   * @param address the start address of the bss segment
   */
  public void setBssStart(int address) {
    this.bssStart = address;
  }

  /**
   * This method adds a new statement to the program.
   *
   * @param stmt the new statement
   * @see vsim.assembler.statements.Statement
   */
  public void add(Statement stmt) {
    this.stmts.add(stmt);
    this.textIndex += Instruction.LENGTH;
  }

  /**
   * This method is used to indicate that the next datum needs to be
   * aligned to a {@code Math.pow(2, alignVal)} byte boundary.
   *
   * @param alignVal the alignment value
   */
  public void align(int alignVal) {
    this.align = true;
    this.alignVal = (int)Math.pow(2, alignVal);
  }

  /**
   * This method is used to indicate that the next datum needs to be
   * aligned to a {@code alignVal} byte boundary.
   *
   * @param alignVal the alignment value
   */
  public void balign(int alignVal) {
    this.align = true;
    this.alignVal = alignVal;
  }

  /**
   * This method tries to add a new global label to this program.
   *
   * @param label the global label name to add
   * @param debug debug information of this label
   * @return true if the label does not already exists, false otherwise
   */
  public boolean addGlobal(String label, DebugInfo debug) {
    if (!this.globals.containsKey(label)) {
      this.globals.put(label, debug);
      return true;
    }
    return false;
  }

  /**
   * This method tries to add a new local label to this program.
   *
   * @param segment the segment of this label
   * @param label the label name
   * @see vsim.assembler.Segment
   * @see vsim.assembler.SymbolTable
   * @return true if the label does not already exists, false otherwise
   */
  public boolean addSymbol(Segment segment, String label) {
    switch (segment) {
      case DATA:
        this.dataIndex = this.align(this.dataIndex, this.data);
        return this.table.add(label, Segment.DATA, this.dataIndex);
      case RODATA:
        this.rodataIndex = this.align(this.rodataIndex, this.rodata);
        return this.table.add(label, Segment.RODATA, this.rodataIndex);
      case BSS:
        this.bssIndex = this.align(this.bssIndex, this.bss);
        return this.table.add(label, Segment.BSS, this.bssIndex);
      default:
        return this.table.add(label, Segment.TEXT, this.textIndex);
    }
  }

  /**
   * This method aligns a segment to a n byte boundary if necessary.
   *
   * @param index the segment current index
   * @param segment the segment data
   * @return the aligned segment index
   */
  private int align(int index, ArrayList<Byte> segment) {
    int padding = 0;
    // calculate padding
    if (this.align) {
      if (Integer.remainderUnsigned(index, this.alignVal) != 0)
        padding = this.alignVal - Integer.remainderUnsigned(index, this.alignVal);
      this.align = false;
    }
    // add padding if necessary
    if (padding != 0) {
      for (int i = 0; i < padding; i++)
        segment.add((byte) 0);
      index += padding;
    }
    // return new segment index
    return index;
  }

  /**
   * This method is used add a new byte to a given segment.
   *
   * @param b the byte to add
   * @param index the current index of the segment
   * @param segment the current segment data
   * @return the new segment index
   */
  private int addTo(byte b, int index, ArrayList<Byte> segment) {
    index = this.align(index, segment);
    segment.add(b);
    index++;
    return index;
  }

  /**
   * This method is used to add a new byte to a given segment.
   *
   * @param segment the segment to add the byte
   * @param b the byte to add
   */
  public void addByte(Segment segment, byte b) {
    switch (segment) {
      case DATA:
        this.dataIndex = this.addTo(b, this.dataIndex, this.data);
        break;
      case RODATA:
        this.rodataIndex = this.addTo(b, this.rodataIndex, this.rodata);
        break;
      case BSS:
        this.bssIndex = this.addTo(b, this.bssIndex, this.bss);
        break;
    }
  }

  /**
   * This method adds a reference (.word label) to the given segment.
   *
   * @param segment the segment to add the reference
   * @param ref relocation to resolve later
   */
  public void addRef(Segment segment, Relocation ref) {
    switch (segment) {
      case DATA:
        this.dataIndex = this.align(this.dataIndex, this.data);
        this.dataAddr.put(this.dataIndex, ref);
        this.dataIndex += Data.WORD_LENGTH;
        this.data.add((byte)0);
        this.data.add((byte)0);
        this.data.add((byte)0);
        this.data.add((byte)0);
        break;
      case RODATA:
        this.rodataIndex = this.align(this.rodataIndex, this.rodata);
        this.rodataAddr.put(this.rodataIndex, ref);
        this.rodataIndex += Data.WORD_LENGTH;
        this.rodata.add((byte)0);
        this.rodata.add((byte)0);
        this.rodata.add((byte)0);
        this.rodata.add((byte)0);
        break;
    }
  }

  /**
   * This method returns the program filename.
   *
   * @return the program filename
   */
  public String getFilename() {
    return this.filename;
  }

  /**
   * This method returns all the global labels.
   *
   * @return all the global labels
   */
  public Set<String> getGlobals() {
    return this.globals.keySet();
  }

  /**
   * This method returns the debug information of a global label.
   *
   * @param label the label name
   * @return debug information of the global label
   */
  public DebugInfo getGlobalDebug(String label) {
    return this.globals.get(label);
  }

  /**
   * This method returns the local symbol table attached to the program.
   *
   * @return the local symbol table
   */
  public SymbolTable getST() {
    return this.table;
  }

  /**
   * This method returns all the data segment bytes of the program.
   *
   * @return the data segment bytes of the program
   */
  public ArrayList<Byte> getData() {
    return this.data;
  }

  /**
   * This method returns all the rodata segment bytes of the program.
   *
   * @return the rodata segment bytes of the program
   */
  public ArrayList<Byte> getRodata() {
    return this.rodata;
  }

  /**
   * This method returns all the bss segment bytes of the program.
   *
   * @return the bss segment bytes of the program
   */
  public ArrayList<Byte> getBss() {
    return this.bss;
  }

  /**
   * This method returns all the statements of the program.
   *
   * @return all the statements of the program
   */
  public ArrayList<Statement> getStatements() {
    return this.stmts;
  }

  /**
   * This method returns the text segment size in bytes.
   *
   * @return the text segment size in bytes
   */
  public int getTextSize() {
    return this.stmts.size() * Instruction.LENGTH;
  }

  /**
   * This method returns the data segment size in bytes.
   * This method includes the data, rodata and bss segments.
   *
   * @return the data segment size in bytes
   */
  public int getDataSize() {
    return this.data.size() + this.rodata.size() + this.bss.size();
  }

}
