package vsim.assembler.statements;


public final class UType extends Statement {

  private String mnemonic;
  private String rd;
  private int imm;

  public UType(String mnemonic, String rd, int imm) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.imm = imm;
  }

}
