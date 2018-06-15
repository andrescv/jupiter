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
    if (imm > 2047 || imm < -2048) {
      int imm_hi = this.imm >>> 12;
      int imm_lo = this.imm & 0xfff;
      stmts.add(new UType("lui", this.debug, this.rd, imm_hi));
      stmts.add(new IType("addi", this.debug, this.rd, this.rd, imm_lo));
    } else {
      stmts.add(new IType("addi", this.debug, this.rd, "x0", this.imm));
    }
    stmts.trimToSize();
    return stmts;
  }

}
