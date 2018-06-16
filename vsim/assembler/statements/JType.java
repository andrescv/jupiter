package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.Assembler;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class JType extends Statement {

  private String rd;
  private Relocation target;

  public JType(String mnemonic, DebugInfo debug, String rd, String target) {
    super(mnemonic, debug);
    this.rd = rd;
    this.target = new Relocation(RelocationType.JAL, target, 0, 31);
  }

  @Override
  public void resolve(String filename) {
    this.target.resolve(0, filename);
  }

  @Override
  public void build(int pc, String filename) {
    Instruction inst = Globals.iset.get(this.mnemonic);
    int rd = Globals.regfile.getRegisterNumber(this.rd);
    int imm = this.target.resolve(pc, filename);
    int opcode = inst.getOpCode();
    this.code.set(InstructionField.IMM_20, imm >>> 20);
    this.code.set(InstructionField.IMM_10_1, imm >>> 1);
    this.code.set(InstructionField.IMM_19_12, imm >>> 12);
    this.code.set(InstructionField.IMM_11J, imm >>> 11);
    this.code.set(InstructionField.OPCODE, opcode);
    this.code.set(InstructionField.RD, rd);
  }

}
