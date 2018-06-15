package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.Relocation;


public final class Tail extends PSeudo {

  private String offset;

  public Tail(String offset) {
    super("tail");
    this.offset = offset;
  }

  public ArrayList<Statement> build(String filename, String source, int lineno) {
    ArrayList<Statement> stmts = new ArrayList<Statement>(2);
    stmts.add(
      new UType(filename, source, lineno,
                "auipc", "x6", new Relocation(this.offset, 12, 31))
    );
    stmts.add(
      new IType(filename, source, lineno,
                "jalr", "x0", "x6", new Relocation(this.offset, 0, 11))
    );
    return stmts;
  }

}
