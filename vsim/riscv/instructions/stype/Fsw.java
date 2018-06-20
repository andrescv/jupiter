package vsim.riscv.instructions.stype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


public final class Fsw extends Instruction {

  public Fsw() {
    super(
      Instruction.Format.S,
      "fsw",
      "fsw rs2, offset(rs1)",
      "set memory[rs1 + sext(offset)] = frd[31:0]"
    );
  }

  @Override
  public int getOpCode() {
    return 0b0100111;
  }

  @Override
  public int getFunct3() {
    return 0b010;
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.fregfile.getRegisterInt(code.get(InstructionField.RS2));
    int imm_11_5 = code.get(InstructionField.IMM_11_5);
    int imm_4_0 = code.get(InstructionField.IMM_4_0);
    int imm = (imm_11_5 << 5) | imm_4_0;
    Globals.memory.storeWord(rs1 + Data.signExtend(imm, 12), rs2);
    Globals.regfile.incProgramCounter();
  }

}
