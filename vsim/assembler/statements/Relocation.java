package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.Assembler;
import vsim.assembler.SymbolTable;


public final class Relocation {

  private String target;
  private int min;
  private int mask;

  public Relocation(String target, int min, int max) {
    this.target = target;
    this.min = min;
    for (int i = min, j = 0; i <= max; i++, j++)
      this.mask |= 1 << j;
  }

  public int resolve(String filename) {
    int address = -1;
    SymbolTable table = Globals.local.get(filename);
    // local lookup
    if (table.get(this.target) != null) {
      address = table.get(this.target);
      address = (address & (this.mask << this.min)) >>> this.min;
    }
    // global lookup
    else if (Globals.globl.get(this.target) != null) {
      address = table.get(this.target);
      address = (address & (this.mask << this.min)) >>> this.min;
    }
    // relocation error
    else {
      Assembler.error(
        "label: '" + this.target + "' used but not defined"
      );
    }
    return address;
  }

}
