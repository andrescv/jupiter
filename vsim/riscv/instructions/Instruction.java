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

  protected Instruction(Format format, String mnemonic, String usage, String description) {
    this.format = format;
    this.mnemonic = mnemonic;
    this.usage = usage;
    this.description = description;
  }

  public abstract void execute(MachineCode code);

  public abstract String disassemble(MachineCode code);

  public Format getFormat() {
    return this.format;
  }

  public int getOpCode() {
    return 0;
  }

  public int getFunct3() {
    return 0;
  }

  public int getFunct5() {
    return 0;
  }

  public int getFunct7() {
    return 0;
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
