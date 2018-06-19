package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class FRType extends Statement {

  private String rd;
  private String rs1;
  private String rs2;

  public FRType(String mnemonic, DebugInfo debug,
               String rd, String rs1, String rs2) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  @Override
  public void resolve(String filename) {
    /* DO NOTHING */
  }

  @Override
  public void build(int pc, String filename) {
    Instruction inst = Globals.iset.get(this.mnemonic);
    int rd  = Globals.fregfile.getRegisterNumber(this.rd);
    int rs1 = Globals.fregfile.getRegisterNumber(this.rs1);
    int rs2 = Globals.fregfile.getRegisterNumber(this.rs2);
    int opcode = inst.getOpCode();
    int funct5 = inst.getFunct5();
    int funct3 = inst.getFunct3();
    this.code.set(InstructionField.FUNCT5, funct5);
    this.code.set(InstructionField.FMT, 0b00);
    this.code.set(InstructionField.RD,  rd);
    this.code.set(InstructionField.RS1, rs1);
    this.code.set(InstructionField.RS2, rs2);
    this.code.set(InstructionField.RM, funct3);
    this.code.set(InstructionField.OPCODE, opcode);
  }

}
