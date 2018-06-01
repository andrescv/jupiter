package vsim.riscv.isa.instructions;

import vsim.utils.Colorize;


public abstract class Instruction {

    private Format format;
    private String mnemonic;
    private String usage;
    private String description;

    public Instruction(Format format, String mnemonic, String usage,
                       String description) {
        this.format = format;
        this.mnemonic = mnemonic;
        this.usage = usage;
        this.description = description;
    }

    public abstract void execute(Code code);

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