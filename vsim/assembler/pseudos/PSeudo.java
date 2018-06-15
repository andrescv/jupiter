package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.assembler.statements.Statement;


public abstract class PSeudo {

  protected String name;
  protected DebugInfo debug;

  public PSeudo(String name, DebugInfo debug) {
    this.name = name;
    this.debug = debug;
  }

  public abstract ArrayList<Statement> build();

  public String getName() {
    return this.name;
  }

}
