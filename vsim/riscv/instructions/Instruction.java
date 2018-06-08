package vsim.riscv.instructions;

import vsim.utils.Colorize;


public abstract class Instruction {

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
