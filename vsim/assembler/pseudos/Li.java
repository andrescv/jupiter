package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;


public final class Li extends PSeudo {

  private String rd;
  private int imm;

  public Li(DebugInfo debug, String rd, int imm) {
    super("li", debug);
    this.rd = rd;
    this.imm = imm;
  }

  public ArrayList<Statement> build() {
    ArrayList<Statement> stmts = new ArrayList<Statement>();
    return stmts;
  }

}
