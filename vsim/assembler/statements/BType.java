package vsim.assembler.statements;

import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class BType extends Statement {

  private String mnemonic;
  private String rs1;
  private String rs2;
  private String offset;

  public BType(String filename, String source, int lineno,
               String mnemonic, String rs1, String rs2, String offset) {
    super(filename, source, lineno);
    this.mnemonic = mnemonic;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.offset = offset;
  }

  @Override
  public void eval() {
    // TODO
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
