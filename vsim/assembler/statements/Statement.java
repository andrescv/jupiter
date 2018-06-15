package vsim.assembler.statements;

import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.MachineCode;


public abstract class Statement {

  protected String mnemonic;
  protected DebugInfo debug;
  protected MachineCode code;

  public Statement(String mnemonic, DebugInfo debug) {
    this.debug = debug;
    this.mnemonic = mnemonic;
    this.code = new MachineCode();
  }

  public abstract void resolve(String filename);

  public abstract void build(int pc, String filename);

  public MachineCode result() {
    return this.code;
  }

  public String getMnemonic() {
    return this.mnemonic;
  }

  public DebugInfo getDebugInfo() {
    return this.debug;
  }

  @Override
  public String toString() {
    return this.code.toString();
  }

}
