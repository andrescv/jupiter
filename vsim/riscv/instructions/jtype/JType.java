package vsim.riscv.instructions.jtype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class JType extends Instruction {

  protected JType(String mnemonic, String usage, String description) {
    super(Instruction.Format.J, mnemonic, usage, description);
  }

  protected abstract int compute(int imm);

  private int getImm(MachineCode code) {
    int imm_10_1 = code.get(InstructionField.IMM_10_1);
    int imm_11 = code.get(InstructionField.IMM_11J);
    int imm_19_12 = code.get(InstructionField.IMM_19_12);
    int imm_20 = code.get(InstructionField.IMM_20);
    int imm = (imm_20 << 19 | imm_19_12 << 11 | imm_11 << 10 | imm_10_1) << 1;
    return Data.signExtend(imm, 21);
  }

  @Override
  public void execute(MachineCode code) {
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(this.getImm(code))
    );
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int imm = this.getImm(code);
    return Colorize.cyan(String.format("%s x%d, %d", op, rd, imm));
  }

}
