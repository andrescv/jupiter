package vsim.assembler.statements;


public final class SType extends Statement {

  private String mnemonic;
  private String rd;
  private String rs1;
  private int imm;

  public SType(String mnemonic, String rd, String rs1, int imm) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

}
