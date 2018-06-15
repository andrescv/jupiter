package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class BType extends Statement {

  private String rs1;
  private String rs2;
  private String offset;

  public BType(String mnemonic, DebugInfo debug,
               String rs1, String rs2, String offset) {
    super(mnemonic, debug);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.offset = offset;
  }

  @Override
  public void eval(String filename) {
    // TODO
  }

}
