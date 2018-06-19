package vsim.riscv.instructions.rtype;

import vsim.Globals;
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
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b11100;
    this.funct3 = 0b000;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      Globals.fregfile.getRegisterInt(code.get(InstructionField.RS1))
    );
    Globals.regfile.incProgramCounter();
  }

}
