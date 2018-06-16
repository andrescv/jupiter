package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.Relocation;
import vsim.assembler.statements.RelocationType;


public final class Call extends PSeudo {

  private String offset;

  public Call(DebugInfo debug, String offset) {
    super("call", debug);
    this.offset = offset;
  }

  public ArrayList<Statement> build() {
    ArrayList<Statement> stmts = new ArrayList<Statement>(2);
    stmts.add(
      new UType(
        "auipc",
        this.debug,
        "x6",
        new Relocation(RelocationType.DEFAULT, this.offset, 12, 31)
      )
    );
    stmts.add(
      new IType(
        "jalr",
        this.debug,
        "x1",
        "x6",
        new Relocation(RelocationType.PCLO, this.offset, 0, 11)
      )
    );
    return stmts;
  }

}
