package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class RType extends Instruction {

  protected RType(String mnemonic, String usage, String description) {
    super(Instruction.Format.R, mnemonic, usage, description);
  }

  protected abstract void compute(int rd, int rs1, int rs2);

  @Override
  public void execute(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    this.compute(rd, rs1, rs2);
    Globals.regfile.incProgramCounter();
  }

}
