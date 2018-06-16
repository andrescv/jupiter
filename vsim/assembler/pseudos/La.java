package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;
import vsim.assembler.statements.Relocation;
import vsim.assembler.statements.RelocationType;


public final class La extends PSeudo {

  private String rd;
  private String id;

  public La(DebugInfo debug, String rd, String id) {
    super("la", debug);
    this.rd = rd;
    this.id = id;
  }

  public ArrayList<Statement> build() {
    ArrayList<Statement> stmts = new ArrayList<Statement>(2);
    stmts.add(
      new UType(
        "auipc",
        this.debug,
        this.rd,
        new Relocation(RelocationType.DEFAULT, this.id, 12, 31)
      )
    );
    stmts.add(
      new IType(
        "addi",
        this.debug,
        this.rd,
        this.rd,
        new Relocation(RelocationType.PCLO, this.id, 0, 11)
      )
    );
    return stmts;
  }

}
