package vsim.assembler.statements;

import vsim.Globals;
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
    SymbolTable table = Globals.local.get(filename);
    if (table.get(this.target) != null) {
      int address = table.get(this.target);
      return (address & (this.mask << this.min)) >>> this.min;
    } else if (Globals.globl.get(this.target) != null) {
      int address = table.get(this.target);
      return (address & (this.mask << this.min)) >>> this.min;
    } else
      Globals.errors.add(
        "label: '" + this.target + "' used but not defined"
      );
    return -1;
  }

}
