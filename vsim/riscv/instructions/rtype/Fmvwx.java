package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fmvwx extends Instruction {

  public Fmvwx() {
    super(
      Instruction.Format.R,
      "fmv.w.x",
      "fmv.w.x frd, rs1",
      "set frd = rs1[31:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct5() {
    return 0b11110;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegisterInt(
      code.get(InstructionField.RD),
      Globals.regfile.getRegister(code.get(InstructionField.RS1))
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return Colorize.cyan(String.format("%s f%d, x%d", op, rd, rs1));
  }

}
