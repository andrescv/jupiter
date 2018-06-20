package vsim.riscv.instructions.btype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class BType extends Instruction {

  protected BType(String mnemonic, String usage, String description) {
    super(Instruction.Format.B, mnemonic, usage, description);
  }

  protected abstract boolean comparison(int rs1, int rs2);

  @Override
  public int getOpCode() {
    return 0b1100011;
  }

  private int getImm(MachineCode code) {
    int imm_4_1 = code.get(InstructionField.IMM_4_1);
    int imm_10_5 = code.get(InstructionField.IMM_10_5);
    int imm_11 = code.get(InstructionField.IMM_11B);
    int imm_12 = code.get(InstructionField.IMM_12);
    int imm = (imm_12 << 11 | imm_11 << 10 | imm_10_5 << 4 | imm_4_1) << 1;
    return Data.signExtend(imm, 13);
  }

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = Globals.regfile.getRegister(code.get(InstructionField.RS2));
    boolean cmp = this.comparison(rs1, rs2);
    if (cmp) {
      int pc = Globals.regfile.getProgramCounter();
      Globals.regfile.setProgramCounter(pc + this.getImm(code));
    } else
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
