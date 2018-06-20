package vsim.riscv.instructions.itype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Flw extends Instruction {

  public Flw() {
    super(
      Instruction.Format.I,
      "flw",
      "flw frd, offset(rs1)",
      "set frd = memory[rs1 + sext(offset)][31:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0000111;
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int imm = code.get(InstructionField.IMM_11_0);
    Globals.fregfile.setRegisterInt(
      code.get(InstructionField.RD),
      Globals.memory.loadWord(rs1 + Data.signExtend(imm, 12))
    );
    Globals.regfile.incProgramCounter();
  }

}
