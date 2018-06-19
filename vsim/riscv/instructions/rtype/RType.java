package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class RType extends Instruction {

  protected RType(String mnemonic, String usage, String description) {
    super(Instruction.Format.R, mnemonic, usage, description);
  }

  protected abstract int compute(int rs1, int rs2);

  @Override
  public int getOpCode() {
    return 0b0110011;
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(rs1, rs2)
    );
    Globals.regfile.incProgramCounter();
  }

}
