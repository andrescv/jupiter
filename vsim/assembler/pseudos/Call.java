package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.Relocation;


public final class Call extends PSeudo {

  private String offset;

  public Call(DebugInfo debug, String offset) {
    super("call", debug);
    this.offset = offset;
  }

  public ArrayList<Statement> build() {
    ArrayList<Statement> stmts = new ArrayList<Statement>(2);
    return stmts;
  }

}
