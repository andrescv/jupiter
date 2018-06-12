package vsim.riscv.instructions;

import vsim.utils.Data;
import vsim.utils.Colorize;


public abstract class Instruction {

  // instruction length
  public static final int LENGTH = Data.WORD_LENGTH;

  // instruction formats
  public static enum Format {R, I, S, B, U, J};

  private Format format;
  private String mnemonic;
  private String usage;
  private String description;
  protected int opcode;
  protected int funct3;
  protected int funct7;

  protected Instruction(Format format, String mnemonic, String usage, String description) {
    this.format = format;
    this.mnemonic = mnemonic;
    this.usage = usage;
    this.description = description;
    this.opcode = 0b0000000;
    this.funct3 = -1;
    this.funct7 = -1;
  }

  public abstract void execute(MachineCode code);

  public int getOpCode() {
    return this.opcode;
  }

  public int getFunct3() {
    return this.funct3;
  }

  public int getFunct7() {
    return this.funct7;
  }

  public Format getFormat() {
    return this.format;
  }

  public String getMnemonic() {
    return this.mnemonic;
  }

  public String getUsage() {
    return this.usage;
  }

  public String getDescription() {
    return this.description;
  }

  @Override
  public String toString() {
    return String.format(
      "[%s] (%s) example: %s",
      Colorize.red(this.format.toString()),
      Colorize.green(this.mnemonic),
      Colorize.cyan(this.usage)
    );
  }

}
