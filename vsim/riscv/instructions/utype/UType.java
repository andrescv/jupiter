package vsim.riscv.instructions.utype;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class UType extends Instruction {

  protected UType(String mnemonic, String usage, String description) {
    super(Instruction.Format.U, mnemonic, usage, description);
  }

  protected abstract int compute(int imm);

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(code.get(InstructionField.IMM_31_12))
    );
  }

}
