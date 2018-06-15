package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class SType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String rs1;
  private String rs2;
  private int imm;

  public SType(String mnemonic, DebugInfo debug,
               String rs1, String rs2, int imm) {
    super(mnemonic, debug);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.imm = imm;
  }

  @Override
  public void resolve(String filename) {
    /* DO NOTHING */
  }

  @Override
  public void build(String filename) {

  }

}
