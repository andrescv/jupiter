package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.ALU;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fclasss extends Instruction {

  public Fclasss() {
    super(
      Instruction.Format.R,
      "fclass.s",
      "fclass.s rd, frs1",
      "set rd = 10-bit mask that indicates the class of the floating-point number"
    );
  }

  @Override
  public int getOpCode() {
    return 0b1010011;
  }

  @Override
  public int getFunct3() {
    return 0b001;
  }

  @Override
  public int getFunct5() {
    return 0b11100;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      ALU.fclass(Globals.fregfile.getRegister(code.get(InstructionField.RS1)))
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
