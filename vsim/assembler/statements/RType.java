package vsim.assembler.statements;


public final class RType extends Statement {

  private String mnemonic;
  private String rd;
  private String rs1;
  private String rs2;

  public RType(String mnemonic, String rd, String rs1, String rs2) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

}
