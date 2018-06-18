package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fclasss extends Instruction {

  protected Fclasss() {
    super(
      Instruction.Format.R,
      "fclass.s",
      "fclass.s rd, frs1",
      "set rd = 10-bit mask that indicates the class of the floating-point number"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      Data.fclass(Globals.fregfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
