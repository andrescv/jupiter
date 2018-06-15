package vsim.assembler.statements;

import vsim.Globals;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class RType extends Statement {

  private String mnemonic;
  private String rd;
  private String rs1;
  private String rs2;

  public RType(String filename, String mnemonic, String rd, String rs1, String rs2) {
    super(filename);
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.rs2 = rs2;
  }

  @Override
  public void eval() {
    Instruction inst = Globals.iset.get(this.mnemonic);
    int rd  = Globals.regfile.getRegisterNumber(this.rd);
    int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
    int rs2 = Globals.regfile.getRegisterNumber(this.rs2);
    int opcode = inst.getOpCode();
    int funct3 = inst.getFunct3();
    int funct7 = inst.getFunct7();
    this.code.set(InstructionField.RD,  rd);
    this.code.set(InstructionField.RS1, rs1);
    this.code.set(InstructionField.RS2, rs2);
    this.code.set(InstructionField.OPCODE, opcode);
    this.code.set(InstructionField.FUNCT3, funct3);
    this.code.set(InstructionField.FUNCT7, funct7);
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
