package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public class IType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String rd;
  private String rs1;
  private Object imm;

  public IType(String mnemonic, DebugInfo debug,
               String rd, String rs1, Object imm) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

  @Override
  public void eval(String filename) {

  }

}
