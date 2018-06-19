package vsim.assembler.statements;

import vsim.Globals;
import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class R4Type extends Statement {

  private String rd;
  private String rs1;
  private String rs2;
  private String rs3;

  public R4Type(String mnemonic, DebugInfo debug,
               String rd, String rs1, String rs2, String rs3) {
    super(mnemonic, debug);
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
    this.rs3 = rs3;
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
    int rs3 = Globals.fregfile.getRegisterNumber(this.rs3);
    int opcode = inst.getOpCode();
    this.code.set(InstructionField.RD,  rd);
    this.code.set(InstructionField.FMT, 0b00);
    this.code.set(InstructionField.RS1, rs1);
    this.code.set(InstructionField.RS2, rs2);
    this.code.set(InstructionField.RS3, rs3);
    this.code.set(InstructionField.RM,  0b111); // dynamic
    this.code.set(InstructionField.OPCODE, opcode);
  }

}
