package vsim.riscv.instructions.btype;

import vsim.utils.Data;
import vsim.simulator.State;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class BType extends Instruction {

  protected BType(String mnemonic, String usage, String description) {
    super(Instruction.Format.B, mnemonic, usage, description);
  }

  protected abstract boolean comparison(int rs1, int rs2);

  @Override
  public void execute(MachineCode code) {
    int rs1 = State.regfile.getRegister(code.get(InstructionField.RS1));
    int rs2 = State.regfile.getRegister(code.get(InstructionField.RS2));
    boolean cmp = this.comparison(rs1, rs2);
    if (cmp) {
      int pc = State.regfile.getProgramCounter();
      int imm_4_1 = code.get(InstructionField.IMM_4_1);
      int imm_10_5 = code.get(InstructionField.IMM_10_5);
      int imm_11 = code.get(InstructionField.IMM_11B);
      int imm_12 = code.get(InstructionField.IMM_12);
      int imm = (imm_12 << 11 | imm_11 << 10 | imm_10_5 << 4 | imm_4_1) << 1;
      State.regfile.setProgramCounter(pc + Data.signExtend(imm, 13));
    }
  }

}
