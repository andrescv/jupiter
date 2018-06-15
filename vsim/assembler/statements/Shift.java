package vsim.assembler.statements;

import vsim.Globals;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.MachineCode;
import vsim.riscv.instructions.InstructionField;


public final class Shift extends Statement {

  private static final int MIN_VAL = 0;
  private static final int MAX_VAL = 31;

  private String mnemonic;
  private String rd;
  private String rs1;
  private int shamt;

  public Shift(String filename, String source, int lineno,
               String mnemonic, String rd, String rs1, int shamt) {
    super(filename, source, lineno);
    this.mnemonic = mnemonic;
    this.rd = rd;
    this.rs1 = rs1;
    this.shamt = shamt;
  }

  @Override
  public void eval() {
    if (!((this.shamt > Shift.MAX_VAL) || (this.shamt < Shift.MIN_VAL))) {
      Instruction inst = Globals.iset.get(this.mnemonic);
      int rd  = Globals.regfile.getRegisterNumber(this.rd);
      int rs1 = Globals.regfile.getRegisterNumber(this.rs1);
      int opcode = inst.getOpCode();
      int funct3 = inst.getFunct3();
      int funct7 = inst.getFunct7();
      this.code.set(InstructionField.RD,  rd);
      this.code.set(InstructionField.RS1, rs1);
      this.code.set(InstructionField.SHAMT, this.shamt);
      this.code.set(InstructionField.OPCODE, opcode);
      this.code.set(InstructionField.FUNCT3, funct3);
      this.code.set(InstructionField.FUNCT7, funct7);
    } else
      Globals.errors.add(
        "instruction: " + this.filename + ": at line: " + this.lineno +
        " shift amount '" + this.shamt + "' out of range should be between 0 and 31"
      );
  }

  @Override
  public MachineCode result() {
    return this.code;
  }

}
