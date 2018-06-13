package vsim.assembler.statements;


public final class JType extends Statement {

  private String mnemonic;
  private String rd;
  private Object imm;

  public JType(String mnemonic, String rd, Object imm) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.imm = imm;
  }

}
