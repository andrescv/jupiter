package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Data;
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
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b11000;
    this.funct3 = 0b111;
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
