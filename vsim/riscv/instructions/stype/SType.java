package vsim.riscv.instructions.stype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class SType extends Instruction {

  protected SType(String mnemonic, String usage, String description) {
    super(Instruction.Format.S, mnemonic, usage, description);
  }

  protected abstract void setMemory(int rs1, int rs2, int imm);

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    int imm_11_5 = code.get(InstructionField.IMM_11_5);
    int imm_4_0 = code.get(InstructionField.IMM_4_0);
    int imm = (imm_11_5 << 5) | imm_4_0;
    this.setMemory(rs1, rs2, Data.signExtend(imm, 12));
    Globals.regfile.incProgramCounter();
  }

}
