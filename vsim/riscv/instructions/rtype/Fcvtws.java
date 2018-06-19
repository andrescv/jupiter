package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.ALU;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtws extends Instruction {

  public Fcvtws() {
    super(
      Instruction.Format.R,
      "fcvt.w.s",
      "fcvt.w.s rd, frs1",
      "set rd = (int)(frs1)"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct3() {
    return 0b111;
  }

  @Override
  public int getFunct5() {
    return 0b11000;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      ALU.fcvtws(Globals.fregfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
