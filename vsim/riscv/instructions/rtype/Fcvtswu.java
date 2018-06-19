package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.ALU;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtswu extends Instruction {

  public Fcvtswu() {
    super(
      Instruction.Format.R,
      "fcvt.s.wu",
      "fcvt.s.wu frd, rs1",
      "set frd = (float)(unsigned(rs1))"
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
    return 0b11010;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      ALU.fcvtswu(Globals.regfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
