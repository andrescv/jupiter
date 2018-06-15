package vsim.assembler.statements;

import vsim.Globals;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;

public final class UType extends Statement {

  private static final int MIN_VAL = 0;
  private static final int MAX_VAL = 1048575;

  private String mnemonic;
  private String rd;
  private Object imm;

  public UType(String filename, String source, int lineno,
               String mnemonic, String rd, Object imm) {
    super(filename, source, lineno);
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.imm = imm;
  }

  @Override
  public void eval() {
    int imm;
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(this.filename);
    else
      imm = (int) this.imm;
    if (!((imm > UType.MAX_VAL) || (imm < UType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd  = Globals.regfile.getRegisterNumber(this.rd);
      int opcode = inst.getOpCode();
      this.code.set(InstructionField.RD,  rd);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.IMM_31_12, imm);
    } else
      Globals.errors.add(
        "instruction: " + this.filename + ": at line: " + this.lineno +
        " immediate '" + this.imm + "' out of range should be between 0 and 1048575"
      );
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
