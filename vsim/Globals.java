package vsim;

import vsim.riscv.hardware.Memory;
import vsim.riscv.hardware.RegisterFile;


public final class Globals {

    // memory
    public static final Memory memory = new Memory();

    // register file
    public static final RegisterFile regfile = new RegisterFile();

}