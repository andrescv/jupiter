package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class BType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String rs1;
  private String rs2;
  private String label;
  private Relocation offset;

  public BType(String mnemonic, DebugInfo debug,
               String rs1, String rs2, String offset) {
    super(mnemonic, debug);
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.label = offset;
    this.offset = new Relocation(RelocationType.BRANCH, offset, 0, 31);
  }

  @Override
  public void resolve(String filename) {
    this.offset.resolve(0, filename);
  }

  @Override
  public void build(int pc, String filename) {
    int imm = this.offset.resolve(pc, filename);
    if (!((imm > BType.MAX_VAL) || (imm < BType.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rs1  = Globals.regfile.getRegisterNumber(this.rs1);
      int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.RS2, rs2);
      this.code.set(InstructionField.IMM_11B, imm >>> 11);
      this.code.set(InstructionField.IMM_4_1, imm >>> 1);
      this.code.set(InstructionField.IMM_12, imm >>> 12);
      this.code.set(InstructionField.IMM_10_5, imm >>> 5);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
    } else
      Assembler.error(
        "branch to '" + this.label + "' too far"
      );
  }

}
