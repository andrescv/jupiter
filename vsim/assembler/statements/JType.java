package vsim.assembler.statements;

import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class JType extends Statement {

  private String mnemonic;
  private String rd;
  private Relocation target;

  public JType(String filename, String mnemonic, String rd, String target) {
    super(filename);
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.target = new Relocation(target, 0, 31);
  }

  @Override
  public void eval() {
    // TODO
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
