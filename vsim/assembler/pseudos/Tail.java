package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.Statement;


public final class Tail extends PSeudo {

  private Object offset;

  public Tail(Object offset) {
    super("tail");
    this.offset = offset;
  }

  public ArrayList<Statement> build() {
    return null;
  }

}
