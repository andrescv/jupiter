package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;

public final class UType extends Statement {

  private static final int MIN_VAL = 0;
  private static final int MAX_VAL = 1048575;

  private String rd;
  private Object imm;

  public UType(String mnemonic, DebugInfo debug, String rd, Object imm) {
    super(mnemonic, debug);
    this.rd = rd;
    this.imm = imm;
  }

  @Override
  public void eval(String filename) {

  }

}
