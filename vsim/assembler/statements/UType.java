package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;

public final class UType extends Statement {

  private static final int MIN_VAL = 0;
  private static final int MAX_VAL = 1048575;

  private String rd;
  private Object imm;

  public UType(String mnemonic, DebugInfo debug, String rd, Object imm) {
    super(mnemonic, debug);
    this.rd = rd;
    this.imm = imm;
  }

  @Override
  public void resolve(String filename) {
    if (this.imm instanceof Relocation)
      ((Relocation) this.imm).resolve(filename);
  }

  @Override
  public void build(int pc, String filename) {
    int imm;
    // get imm
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(filename);
    else
      imm = (int) this.imm;
    // check range
    if (!((imm > UType.MAX_VAL) || (imm < UType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd = Globals.regfile.getRegisterNumber(this.rd);
      int opcode = inst.getOpCode();
      this.code.set(InstructionField.RD, rd);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.IMM_31_12, imm);
    } else
      Assembler.error(
        "immediate '" + imm + "' out of range should be between 0 and 1048575"
      );
  }

}
