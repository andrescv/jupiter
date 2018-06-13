package vsim.assembler.statements;


public final class BType extends Statement {

  private String mnemonic;
  private String rs1;
  private String rs2;
  private Object imm;

  public BType(String mnemonic, String rs1, String rs2, Object imm) {
    this.mnemonic = mnemonic;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.imm = imm;
  }

}
