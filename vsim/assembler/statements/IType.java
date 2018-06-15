package vsim.assembler.statements;

import vsim.Globals;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public class IType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String mnemonic;
  private String rd;
  private String rs1;
  private Object imm;

  public IType(String filename, String source, int lineno,
               String mnemonic, String rd, String rs1, Object imm) {
    super(filename, source, lineno);
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

  @Override
  public void eval() {
    int imm;
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(this.filename);
    else
      imm = (int) this.imm;
    if (!((imm > IType.MAX_VAL) || (imm < IType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd  = Globals.regfile.getRegisterNumber(this.rd);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      this.code.set(InstructionField.RD,  rd);
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.IMM_11_0, imm);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
    } else
      Globals.errors.add(
        "instruction: " + this.filename + ": at line: " + this.lineno +
        " immediate '" + this.imm + "' out of range should be between -2048 and 2047"
      );
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
