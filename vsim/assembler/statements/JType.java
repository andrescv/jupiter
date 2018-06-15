package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class JType extends Statement {

  private String rd;
  private Relocation target;

  public JType(String mnemonic, DebugInfo debug, String rd, String target) {
    super(mnemonic, debug);
    this.rd = rd;
    this.target = new Relocation(target, 0, 31);
  }

  @Override
  public void eval(String filename) {
    // TODO
  }

}
