package vsim.riscv.instructions.frtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Flts extends Instruction {

  public Flts() {
    super(
      Instruction.Format.R,
      "flt.s",
      "flt.s rd, frs1, frs2",
      "set rd = 1 if frs1 < frs2 else 0"
    );
    // set opcode
    this.opcode = 0b1010011;
    this.funct5 = 0b10100;
    this.funct3 = 0b001;
  }

  @Override
  public void execute(MachineCode code) {
    float rs1 = Globals.fregfile.getRegister(code.get(InstructionField.RS1));
    float rs2 = Globals.fregfile.getRegister(code.get(InstructionField.RS2));
    int result = (rs1 < rs2) ? 1 : 0;
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      result
    );
    Globals.regfile.incProgramCounter();
  }

}
