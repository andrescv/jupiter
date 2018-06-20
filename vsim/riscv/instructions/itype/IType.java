package vsim.riscv.instructions.itype;

import vsim.Globals;
import vsim.utils.Data;
import vsim.utils.Colorize;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;


abstract class IType extends Instruction {

  protected IType(String mnemonic, String usage, String description) {
    super(Instruction.Format.I, mnemonic, usage, description);
  }

  protected abstract int compute(int rs1, int imm);

  @Override
  public void execute(MachineCode code) {
    int rs1 = Globals.regfile.getRegister(code.get(InstructionField.RS1));
    int imm = Data.signExtend(code.get(InstructionField.IMM_11_0), 12);
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(rs1, imm)
    );
    if (!this.getMnemonic().equals("jalr"))
      Globals.regfile.incProgramCounter();
  }

  @Override
  public String disassemble(MachineCode code) {
    String op = this.getMnemonic();
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int imm = Data.signExtend(code.get(InstructionField.IMM_11_0), 12);
    return Colorize.cyan(String.format("%s x%d, x%d, %d", op, rd, rs1, imm));
  }

}
