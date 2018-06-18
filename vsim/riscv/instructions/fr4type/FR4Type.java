package vsim.riscv.instructions.fr4type;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class FR4Type extends Instruction {

  protected FR4Type(String mnemonic, String usage, String description) {
    super(Instruction.Format.R, mnemonic, usage, description);
  }

  protected abstract float compute(float rs1, float rs2, float rs3);

  @Override
  public void execute(MachineCode code) {
    float rs1 = Globals.fregfile.getRegister(code.get(InstructionField.RS1));
    float rs2 = Globals.fregfile.getRegister(code.get(InstructionField.RS2));
    float rs3 = Globals.fregfile.getRegister(code.get(InstructionField.RS3));
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(rs1, rs2, rs3)
    );
    Globals.regfile.incProgramCounter();
  }

}
