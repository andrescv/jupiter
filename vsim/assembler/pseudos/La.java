package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.Statement;


public final class La extends PSeudo {

  private String rd;
  private String id;

  public La(String rd, String id) {
    super("la");
    this.rd = rd;
    this.id = id;
  }

  public ArrayList<Statement> build() {
    return null;
  }

}
