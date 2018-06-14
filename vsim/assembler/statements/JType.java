package vsim.assembler.statements;


public final class JType extends Statement {

  private String mnemonic;
  private String rd;
  private String target;

  public JType(String mnemonic, String rd, String target) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.target = target;
  }

}
