package vsim.assembler.statements;


public final class BType extends Statement {

  private String mnemonic;
  private String rs1;
  private String rs2;
  private String offset;

  public BType(String mnemonic, String rs1, String rs2, String offset) {
    this.mnemonic = mnemonic;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.offset = offset;
  }

}
