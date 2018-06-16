package vsim.riscv.instructions.itype;

import vsim.Globals;
import vsim.utils.Data;
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
    int imm = code.get(InstructionField.IMM_11_0);
    Globals.regfile.setRegister(
      code.get(InstructionField.RD),
      this.compute(rs1, Data.signExtend(imm, 12))
    );
    if (!this.mnemonic.equals("jalr"))
      Globals.regfile.incProgramCounter();
  }

}
