package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.Relocation;


public final class La extends PSeudo {

  private String rd;
  private String id;

  public La(String rd, String id) {
    super("la");
    this.rd = rd;
    this.id = id;
  }

  public ArrayList<Statement> build(String filename) {
    ArrayList<Statement> stmts = new ArrayList<Statement>(2);
    stmts.add(
      new UType(filename, "auipc", this.rd, new Relocation(this.id, 12, 31))
    );
    stmts.add(
      new IType(filename, "addi", this.rd, this.rd, new Relocation(this.id, 0, 11))
    );
    return stmts;
  }

}
