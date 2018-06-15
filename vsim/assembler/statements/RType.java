package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class RType extends Statement {

  private String rd;
  private String rs1;
  private String rs2;

  public RType(String mnemonic, DebugInfo debug,
               String rd, String rs1, String rs2) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  @Override
  public void resolve(String filename) {
    /* DO NOTHING */
  }

  @Override
  public void build(String filename) {

  }

}
