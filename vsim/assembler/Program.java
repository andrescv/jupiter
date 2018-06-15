package vsim.assembler;

import vsim.utils.Message;
import java.util.ArrayList;
import java.util.Enumeration;
import vsim.riscv.instructions.Instruction;
import vsim.assembler.statements.Statement;


public final class Program {

  // program statements
  private ArrayList<Statement> stmts;
  private int textIndex;
  private int textStart;

  // align directive
  private boolean align;
  private boolean balign;
  private int alignVal;

  // program filename
  private String filename;

  // symbols control
  private ArrayList<String> globals;
  private SymbolTable table;

  // segments control
  private Segment segment;
  private int dataIndex;
  private int dataStart;
  private ArrayList<Byte> data;
  private int rodataIndex;
  private int rodataStart;
  private ArrayList<Byte> rodata;
  private int bssIndex;
  private int bssStart;
  private ArrayList<Byte> bss;

  protected Program(String filename) {
    this.filename = filename;
    // align
    this.align = false;
    this.balign = false;
    this.alignVal = 0;
    // symbols
    this.table = new SymbolTable();
    this.globals = new ArrayList<String>();
    // segments
    this.data = new ArrayList<Byte>();
    this.dataIndex = 0;
    this.dataStart = 0;
    this.rodata = new ArrayList<Byte>();
    this.rodataIndex = 0;
    this.rodataStart = 0;
    this.bss = new ArrayList<Byte>();
    this.bssIndex = 0;
    this.bssStart = 0;
    // statements
    this.stmts = new ArrayList<Statement>();
    this.textIndex = 0;
    this.textStart = 0;
  }

  protected void add(Statement stmt) {
    this.stmts.add(stmt);
    this.textIndex += Instruction.LENGTH;
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

  public void setTextStart(int address) {
    this.textStart = address;
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

  public void relocateSymbols() {
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
  }

  public ArrayList<Statement> getStatements() {
    return this.stmts;
  }

  public int textSize() {
    return this.stmts.size() * Instruction.LENGTH;
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

  protected void alignVal(int alignVal) {
    this.align = true;
    this.alignVal = (int)Math.pow(2, alignVal);
  }

  protected void balignVal(int alignVal) {
    this.align = true;
    this.balign = true;
    this.alignVal = alignVal;
  }

  private int align(int index, ArrayList<Byte> segment) {
    if (this.align) {
      if (this.balign || ((index % this.alignVal) != 0)) {
        int padding;
        if (this.balign)
          padding = this.alignVal;
        else
          padding = this.alignVal - index % this.alignVal;
        for (int i = 0; i < padding; i++)
          segment.add((byte) 0);
        index += padding;
      }
      this.align = false;
      this.balign = false;
    }
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

  protected void addToBss(byte b) {
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

  @Override
  public String toString() {
    int textSize = this.stmts.size() * Instruction.LENGTH;
    int dataSize = this.data.size() + this.rodata.size() + this.bss.size();
    return String.format(
      "RISC-V Program (%s, text size: %d, data size: %d bytes)\n\n\n%s",
      this.filename,
      textSize,
      dataSize,
      this.table.toString()
    );
  }

}
