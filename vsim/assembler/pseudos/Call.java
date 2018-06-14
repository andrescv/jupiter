package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.Statement;


public final class Call extends PSeudo {

  private Object offset;

  public Call(Object offset) {
    super("call");
    this.offset = offset;
  }

  public ArrayList<Statement> build() {
    return null;
  }

}
