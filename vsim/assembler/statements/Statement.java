package vsim.assembler.statements;

import vsim.riscv.instructions.MachineCode;


public abstract class Statement {

  protected String filename;
  protected MachineCode code;

  public Statement(String filename) {
    this.filename = filename;
    this.code = new MachineCode();
  }

  public abstract void eval();

  public abstract MachineCode result();

  @Override
  public String toString() {
    return this.code.toString();
  }

}
