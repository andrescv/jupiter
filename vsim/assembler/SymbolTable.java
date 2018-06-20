package vsim.assembler;

import vsim.utils.Colorize;
import java.util.Hashtable;
import java.util.Enumeration;


public final class SymbolTable {

  private Hashtable<String, Sym> table;

  public SymbolTable() {
    this.table = new Hashtable<String, Sym>();
  }

  public void reset() {
    this.table = new Hashtable<String, Sym>();
  }

  public void print() {
    System.out.println(this);
  }

  public Integer get(String label) {
    if (this.table.containsKey(label))
      return this.table.get(label).getAddress();
    return null;
  }

  public Sym getSymbol(String label) {
    return this.table.get(label);
  }

  public boolean set(String label, int address) {
    if (this.table.containsKey(label)) {
      this.table.get(label).setAddress(address);
      return true;
    }
    return false;
  }

  public boolean add(String label, Sym sym) {
    if (!this.table.containsKey(label)) {
      this.table.put(label, sym);
      return true;
    }
    return false;
  }

  public boolean add(String label, Segment segment, int address) {
    if (!this.table.containsKey(label)) {
      this.table.put(label, new Sym(segment, address));
      return true;
    }
    return false;
  }

  public Enumeration<String> labels() {
    return this.table.keys();
  }

  @Override
  public String toString() {
    String out = "";
    String newline = System.getProperty("line.separator");
    for (Enumeration<String> e = this.table.keys(); e.hasMoreElements();) {
      String label = e.nextElement();
      out += Colorize.green(label) + " " + this.table.get(label).toString();
      out += newline;
    }
    return out.replaceAll("\\s+$", "");
  }

}
