package vsim.assembler;

import vsim.utils.Message;
import java.util.ArrayList;
import vsim.assembler.statements.Statement;


public final class Program {

  // program statements
  private ArrayList<Statement> stmts;

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
  private ArrayList<Byte> data;
  private int rodataIndex;
  private ArrayList<Byte> rodata;
  private int bssIndex;
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
    this.segment = Segment.TEXT;
    this.data = new ArrayList<Byte>();
    this.dataIndex = 0;
    this.rodata = new ArrayList<Byte>();
    this.rodataIndex = 0;
    this.bss = new ArrayList<Byte>();
    this.bssIndex = 0;
    // statements
    this.stmts = new ArrayList<Statement>();
  }

  protected void add(Statement stmt) {
    this.stmts.add(stmt);
  }

  protected boolean addGlobal(String label) {
    if (!this.globals.contains(label)) {
      this.globals.add(label);
      return true;
    }
    return false;
  }

  protected boolean addSymbol(String label) {
    switch (this.segment) {
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
        return this.table.add(label, Segment.TEXT, 0);
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
        int padding = this.alignVal - index % this.alignVal;
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

  protected void addByte(byte b) {
    switch (this.segment) {
      case DATA:
        this.addToData(b); break;
      case RODATA:
        this.addToRodata(b); break;
      case BSS:
        this.addToBss(b); break;
    }
  }

  protected void setSegment(Segment segment) {
    this.segment = segment;
  }

  protected boolean inTextSegment() {
    return this.segment == Segment.TEXT;
  }

  protected boolean inDataSegment() {
    return this.segment == Segment.DATA;
  }

  protected boolean inRodataSegment() {
    return this.segment == Segment.RODATA;
  }

  protected boolean inBssSegment() {
    return this.segment == Segment.BSS;
  }

  protected String getSegment() {
    return this.segment.toString().toLowerCase();
  }

  public String getFilename() {
    return this.filename;
  }

  public ArrayList<String> getGlobals() {
    return this.globals;
  }

  @Override
  public String toString() {
    int textSize = this.stmts.size() * 4;
    int dataSize = this.data.size() + this.rodata.size() + this.bss.size();
    return String.format(
      "RISC-V Program (%s, text size: %d, data size: %d bytes)",
      this.filename,
      textSize,
      dataSize
    );
  }

}
