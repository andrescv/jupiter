package vsim.assembler;

import vsim.Globals;
import vsim.utils.Message;
import java.util.ArrayList;
import java.util.Enumeration;
import vsim.riscv.instructions.Instruction;
import vsim.assembler.statements.Statement;


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

  public void setTextStart(int address) {
    this.textStart = address;
  }

  public void setDataStart(int address) {
    this.dataStart = address;
  }

  public void setRodataStart(int address) {
    this.rodataStart = address;
  }

  public void setBssStart(int address) {
    this.bssStart = address;
  }

  protected void add(Statement stmt) {
    this.stmts.add(stmt);
    this.textIndex += Instruction.LENGTH;
  }

  protected void align(int alignVal) {
    this.align = true;
    this.alignVal = (int)Math.pow(2, alignVal);
  }

  protected void balign(int alignVal) {
    this.align = true;
    this.alignVal = alignVal;
  }

  protected boolean addGlobal(String label) {
    if (!this.globals.contains(label)) {
      this.globals.add(label);
      return true;
    }
    return false;
  }

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

  private int addTo(byte b, int index, ArrayList<Byte> segment) {
    index = this.align(index, segment);
    segment.add(b);
    index++;
    return index;
  }

  private void addToData(byte b) {
    this.dataIndex = this.addTo(b, this.dataIndex, this.data);
  }

  private void addToRodata(byte b) {
    this.rodataIndex = this.addTo(b, this.rodataIndex, this.rodata);
  }

  private void addToBss(byte b) {
    this.bssIndex = this.addTo(b, this.rodataIndex, this.bss);
  }

  protected void addByte(Segment segment, byte b) {
    switch (segment) {
      case DATA:
        this.addToData(b); break;
      case RODATA:
        this.addToRodata(b); break;
      case BSS:
        this.addToBss(b); break;
    }
  }

  public String getFilename() {
    return this.filename;
  }

  public ArrayList<String> getGlobals() {
    return this.globals;
  }

  public SymbolTable getST() {
    return this.table;
  }

  public ArrayList<Byte> getData() {
    return this.data;
  }

  public ArrayList<Byte> getRodata() {
    return this.rodata;
  }

  public ArrayList<Byte> getBss() {
    return this.bss;
  }

  public ArrayList<Statement> getStatements() {
    return this.stmts;
  }

  public int getTextSize() {
    return this.stmts.size() * Instruction.LENGTH;
  }

  public int getDataSize() {
    return this.data.size() + this.rodata.size() + this.bss.size();
  }

  @Override
  public String toString() {
    return String.format(
      "RISC-V Program (%s, text: %d bytes, data: %d bytes)",
      this.filename,
      this.getTextSize(),
      this.getDataSize()
    );
  }

}
