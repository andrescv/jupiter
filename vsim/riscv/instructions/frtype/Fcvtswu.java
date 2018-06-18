package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtswu extends Instruction {

  protected Fcvtswu() {
    super(
      Instruction.Format.R,
      "fcvt.s.wu",
      "fcvt.s.wu frd, rs1",
      "set frd = (float)(unsigned(rs1))"
    );
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      Data.fcvtswu(Globals.regfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
