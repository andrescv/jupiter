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
import vsim.utils.Message;
import java.util.ArrayList;
import java.util.Enumeration;
import vsim.riscv.instructions.Instruction;
import vsim.assembler.statements.Statement;


/**
 * The class Program is used to represent an assembler program
 * before becoming a linked program.
 */
public final class Program {

  // program filename
  private String filename;

  // program statements
  private int textIndex;
  private int textStart;
  private ArrayList<Statement> stmts;

  // align directives
  private boolean align;
  private int alignVal;

  // symbols control
  private ArrayList<String> globals;
  private SymbolTable table;

  // data segment control
  private int dataIndex;
  private int dataStart;
  private ArrayList<Byte> data;

  // rodata segment control
  private int rodataIndex;
  private int rodataStart;
  private ArrayList<Byte> rodata;

  // bss segment control
  private int bssIndex;
  private int bssStart;
  private ArrayList<Byte> bss;

  /**
   * Unique constructor that initializes a newly and empty Program object.
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
    this.globals = new ArrayList<String>();
    // data segment control
    this.dataIndex = 0;
    this.dataStart = 0;
    this.data = new ArrayList<Byte>();
    // rodata segment control
    this.rodataIndex = 0;
    this.rodataStart = 0;
    this.rodata = new ArrayList<Byte>();
    // bss segment control
    this.bssIndex = 0;
    this.bssStart = 0;
    this.bss = new ArrayList<Byte>();
  }

  /**
   * This method is used to relocate all symbols of the program and is called
   * inside the {@link vsim.linker.Linker} class.
   */
  public void relocateSymbols() {
    // first locals
    for (Enumeration<String> e = this.table.labels(); e.hasMoreElements();) {
      String label = e.nextElement();
      Sym sym = this.table.getSymbol(label);
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
    for (String global: this.globals)
      Globals.globl.set(global, this.table.get(global));
  }

  /**
   * This method sets the text segment start address of this program.
   *
   * @param address the start address
   */
  public void setTextStart(int address) {
    this.textStart = address;
  }

  /**
   * This method sets the data segment start address of this program.
   *
   * @param address the start address
   */
  public void setDataStart(int address) {
    this.dataStart = address;
  }

  /**
   * This method sets the rodata segment start address of this program.
   *
   * @param address the start address
   */
  public void setRodataStart(int address) {
    this.rodataStart = address;
  }

  /**
   * This method sets the bss segment start address of this program.
   *
   * @param address the start address
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
  protected void add(Statement stmt) {
    this.stmts.add(stmt);
    this.textIndex += Instruction.LENGTH;
  }

  /**
   * This method is used to indicate that the next datum needs to be
   * aligned to a 2 ^ n byte boundary.
   *
   * @param alignVal the alignment value
   */
  protected void align(int alignVal) {
    this.align = true;
    this.alignVal = (int)Math.pow(2, alignVal);
  }

  /**
   * This method is used to indicate that the next datum needs to be
   * aligned to a n byte boundary.
   *
   * @param alignVal the alignment value
   */
  protected void balign(int alignVal) {
    this.align = true;
    this.alignVal = alignVal;
  }

  /**
   * This method tries to add a new global label attached to this program.
   *
   * @param label the global label
   * @return true if the label does not already exists, false otherwise
   */
  protected boolean addGlobal(String label) {
    if (!this.globals.contains(label)) {
      this.globals.add(label);
      return true;
    }
    return false;
  }

  /**
   * This method tries to add a new local label attached to this program.
   *
   * @param segment the segment of this label
   * @param label the label
   * @see vsim.assembler.Segment
   * @see vsim.assembler.SymbolTable
   * @return true if the label does not already exists, false otherwise
   */
  protected boolean addSymbol(Segment segment, String label) {
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
      if ((index % this.alignVal) != 0)
        padding = this.alignVal - index % this.alignVal;
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
  protected void addByte(Segment segment, byte b) {
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
  public ArrayList<String> getGlobals() {
    this.globals.trimToSize();
    return this.globals;
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
   * This method returns all the data segment content of the program.
   *
   * @return the data segment content of the program
   */
  public ArrayList<Byte> getData() {
    return this.data;
  }

  /**
   * This method returns all the rodata segment content of the program.
   *
   * @return the rodata segment content of the program
   */
  public ArrayList<Byte> getRodata() {
    return this.rodata;
  }

  /**
   * This method returns all the bss segment content of the program.
   *
   * @return the bss segment content of the program
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

}
