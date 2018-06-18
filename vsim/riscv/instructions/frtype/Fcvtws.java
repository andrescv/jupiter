package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtws extends Instruction {

  protected Fcvtws() {
    super(
      Instruction.Format.R,
      "fcvt.w.s",
      "fcvt.w.s rd, frs1",
      "set rd = (int)(frs1)"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      Data.fcvtws(Globals.fregfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
