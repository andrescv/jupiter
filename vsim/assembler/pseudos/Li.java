package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.Statement;


public final class Li extends PSeudo {

  private String rd;
  private int imm;

  public Li(String rd, int imm) {
    super("li");
    this.rd = rd;
    this.imm = imm;
  }

  public ArrayList<Statement> build() {
    return null;
  }

}
