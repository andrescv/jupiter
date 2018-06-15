package vsim.assembler.statements;

import vsim.Globals;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class SType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String mnemonic;
  private String rs1;
  private String rs2;
  private int imm;

  public SType(String filename, String source, int lineno,
               String mnemonic, String rd, String rs1, int imm) {
    super(filename, source, lineno);
    this.mnemonic = mnemonic;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.imm = imm;
  }

  @Override
  public void eval() {
    if (!((this.imm > SType.MAX_VAL) || (this.imm < SType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.RS2, rs2);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
      this.code.set(InstructionField.IMM_4_0, this.imm);
      this.code.set(InstructionField.IMM_11_5, this.imm >> 5);
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
