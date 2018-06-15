package vsim.assembler.statements;

import vsim.riscv.instructions.MachineCode;


public abstract class Statement {

  protected int lineno;
  protected String source;
  protected String filename;
  protected MachineCode code;

  public Statement(String filename, String source, int lineno) {
    this.lineno = lineno;
    this.filename = filename;
    this.source = source;
    this.code = new MachineCode();
  }

  public abstract void eval();

  public abstract MachineCode result();

  @Override
  public String toString() {
    return this.code.toString();
  }

}
