package vsim.assembler.statements;

import vsim.Globals;
import vsim.linker.Relocation;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public class IType extends Statement {

  private static final int MIN_VAL = -2048;
  private static final int MAX_VAL = 2047;

  private String rd;
  private String rs1;
  private Object imm;

  public IType(String mnemonic, DebugInfo debug,
               String rd, String rs1, Object imm) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.imm = imm;
  }

  @Override
  public void resolve(String filename) {
    if (this.imm instanceof Relocation)
      ((Relocation) this.imm).resolve(0, filename);
  }

  @Override
  public void build(int pc, String filename) {
    int imm;
    // get imm
    if (this.imm instanceof Relocation)
      imm = ((Relocation) this.imm).resolve(pc, filename);
    else
      imm = (int) this.imm;
    // check range
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
      Assembler.error(
        "immediate '" + imm + "' out of range should be between -2048 and 2047"
      );
  }

}
