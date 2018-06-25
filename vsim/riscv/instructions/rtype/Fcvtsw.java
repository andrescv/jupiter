package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fcvtsw extends Instruction {

  public Fcvtsw() {
    super(
      Instruction.Format.R,
      "fcvt.s.w",
      "fcvt.s.w frd, rs1",
      "set frd = (float)rs1"
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
    int value = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      ((Integer) value).floatValue()
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
