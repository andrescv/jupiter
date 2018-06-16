package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.Assembler;
import vsim.assembler.SymbolTable;


public final class Relocation {

  private String target;
  private int min;
  private int mask;
  private RelocationType type;

  public Relocation(RelocationType type, String target, int min, int max) {
    this.target = target;
    this.min = min;
    this.type = type;
    for (int i = min, j = 0; i <= max; i++, j++)
      this.mask |= 1 << j;
  }

  public int getTargetAddress(String filename) {
    int address = -1;
    SymbolTable table = Globals.local.get(filename);
    // local lookup
    if (table.get(this.target) != null) {
      address = table.get(this.target);
    }
    // global lookup
    else if (Globals.globl.get(this.target) != null) {
      address = table.get(this.target);
    }
    // relocation error
    else {
      Assembler.error(
        "label: '" + this.target + "' used but not defined"
      );
    }
    return address;
  }

  public int resolve(int pc, String filename) {
    int target = this.getTargetAddress(filename);
    target = (target & (this.mask << this.min)) >>> this.min;
    // correct target given a relocation type
    switch (this.type) {
      case PCLO:
        target -= (pc - 4);
        break;
      case JAL:
      case BRANCH:
        target -= pc;
        break;
      case DEFAULT:
        /* NOTHING */
        break;
    }
    return target;
  }

}
