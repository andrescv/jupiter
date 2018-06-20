package vsim.riscv.instructions.stype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class SType extends Instruction {

  protected SType(String mnemonic, String usage, String description) {
    super(Instruction.Format.S, mnemonic, usage, description);
  }

  @Override
  public int getOpCode() {
    return 0b0100011;
  }

  protected abstract void setMemory(int rs1, int rs2, int imm);

  private int getImm(MachineCode code) {
    int imm_11_5 = code.get(InstructionField.IMM_11_5);
    int imm_4_0 = code.get(InstructionField.IMM_4_0);
    int imm = (imm_11_5 << 5) | imm_4_0;
    return Data.signExtend(imm, 12);
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    this.setMemory(rs1, rs2, this.getImm(code));
    Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int imm = this.getImm(code);
    return Colorize.cyan(String.format("%s x%d, x%d, %d", op, rs1, rs2, imm));
  }

}
