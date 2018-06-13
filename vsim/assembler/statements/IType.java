package vsim.assembler.statements;


public final class IType extends Statement {

  private String mnemonic;
  private String rd;
  private String rs1;
  private int imm;

  public IType(String mnemonic, String rd, String rs1, int imm) {
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

}
