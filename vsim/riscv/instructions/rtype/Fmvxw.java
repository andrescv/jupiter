package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fmvxw extends Instruction {

  public Fmvxw() {
    super(
      Instruction.Format.R,
      "fmv.x.w",
      "fmv.x.w rd, frs1",
      "set rd = frs1[31:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct5() {
    return 0b11100;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      Globals.fregfile.getRegisterInt(code.get(InstructionField.RS1))
    );
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    return Colorize.cyan(String.format("%s x%d, f%d", op, rd, rs1));
  }

}
