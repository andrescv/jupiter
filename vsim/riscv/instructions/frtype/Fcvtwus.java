package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtwus extends Instruction {

  protected Fcvtwus() {
    super(
      Instruction.Format.R,
      "fcvt.wu.s",
      "fcvt.wu.s rd, frs1",
      "set rd = (int)(unsigned(frs1))"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      Data.fcvtwus(Globals.fregfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
