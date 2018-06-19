package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Data;
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
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b11010;
    this.funct3 = 0b111;
  }

  @Override
  public void execute(MachineCode code) {
    Globals.fregfile.setRegister(
      code.get(InstructionField.RD),
      Data.fcvtsw(Globals.regfile.getRegister(code.get(InstructionField.RS1)))
    );
    Globals.regfile.incProgramCounter();
  }

}
