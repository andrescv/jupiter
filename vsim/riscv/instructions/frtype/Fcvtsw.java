package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtsw extends Instruction {

  protected Fcvtsw() {
    super(
      Instruction.Format.R,
      "fcvt.s.w",
      "fcvt.s.w frd, rs1",
      "set frd = (float)rs1"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      Data.fcvtsw(Globals.regfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
