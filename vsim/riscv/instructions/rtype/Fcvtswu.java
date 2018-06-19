package vsim.riscv.instructions.rtype;

import vsim.Globals;
import vsim.utils.Data;
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
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b11010;
    this.funct3 = 0b111;
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
