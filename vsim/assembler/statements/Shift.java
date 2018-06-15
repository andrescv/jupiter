package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class Shift extends Statement {

  private static final int MIN_VAL = 0;
  private static final int MAX_VAL = 31;

  private String rd;
  private String rs1;
  private int shamt;

  public Shift(String mnemonic, DebugInfo debug,
               String rd, String rs1, int shamt) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.shamt = shamt;
  }

  @Override
  public void eval(String filename) {

  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
