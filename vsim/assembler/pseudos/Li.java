package vsim.assembler.pseudos;

import java.util.ArrayList;
import vsim.assembler.statements.UType;
import vsim.assembler.statements.IType;
import vsim.assembler.statements.Statement;


public final class Li extends PSeudo {

  private String rd;
  private int imm;

  public Li(String rd, int imm) {
    super("li");
    this.rd = rd;
    this.imm = imm;
  }

  public ArrayList<Statement> build(String filename) {
    ArrayList<Statement> stmts = new ArrayList<Statement>();
    if (imm > 2047 || imm < -2048) {
      int imm_hi = this.imm >>> 12;
      int imm_lo = this.imm & 0xfff;
      stmts.add(new UType(filename, "lui", this.rd, imm_hi));
      stmts.add(new IType(filename, "addi", this.rd, this.rd, imm_lo));
    } else {
      stmts.add(new IType(filename, "addi", this.rd, "x0", this.imm));
    }
    stmts.trimToSize();
    return stmts;
  }

}
