package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fmvwx extends Instruction {

  protected Fmvwx() {
    super(
      Instruction.Format.R,
      "fmv.w.x",
      "fmv.w.x frd, rs1",
      "set frd = rs1[31:0]"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegisterInt(
      code.get(InstructionField.RD),
      Globals.regfile.getRegister(code.get(InstructionField.RS1))
    );
    Globals.regfile.incProgramCounter();
  }

}
