package vsim.assembler;

import vsim.utils.Colorize;
import java.util.Hashtable;
import java.util.Enumeration;


public final class SymbolTable {

  private Hashtable<String, Integer> table;

  public SymbolTable() {
    this.table = new Hashtable<String, Integer>();
  }

  public void reset() {
    this.table = new Hashtable<String, Integer>();
  }

  public boolean set(String label, int address) {
    if (this.table.containsKey(label)) {
      this.table.replace(label, address);
      return true;
    }
    return false;
  }

  public boolean add(String label, int address) {
    if (!this.table.containsKey(label)) {
      this.table.put(label, address);
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    String out = "";
    for (Enumeration<String> e = this.table.keys(); e.hasMoreElements();) {
      String label = e.nextElement();
      String address = String.format("0x%08x", this.table.get(label));
      out += Colorize.green(label) + " @ " + Colorize.purple(address);
    }
    return out;
  }

}
