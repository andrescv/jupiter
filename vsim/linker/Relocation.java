package vsim.linker;

import vsim.Globals;
import vsim.utils.Data;
import vsim.assembler.Assembler;
import vsim.assembler.SymbolTable;


public final class Relocation {

  public static final int PCRELHI = 0;
  public static final int PCRELLO = 1;
  public static final int DEFAULT = 2;

  private int type;
  private String target;

  public Relocation(int type, String target) {
    this.type = type;
    this.target = target;
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
      address = Globals.globl.get(this.target);
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
    int delta = target - pc;
    // correct target given a relocation type
    switch (this.type) {
      case PCRELHI:
        target = (delta >>> 12) + ((delta >>> 11) & 0x1);
        break;
      case PCRELLO:
        target = Data.signExtend(((delta + 4) & 0xfff), 12);
        break;
      case DEFAULT:
        target = delta;
        break;
    }
    return target;
  }

}
