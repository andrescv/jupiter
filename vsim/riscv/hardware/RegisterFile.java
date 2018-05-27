package vsim.riscv.hardware;

import vsim.utils.Colorize;


public class RegisterFile {

    public static final int NUM_REGISTERS = 32;

    private static final String[] REG_NAMES = {
        "zero", "ra", "sp", "gp",
        "tp", "t0", "t1", "t2",
        "s0/fp", "s1", "a0", "a1",
        "a2", "a3", "a4", "a5",
        "a6", "a7", "s2", "s3",
        "s4", "s5", "s6", "s7",
        "s8", "s9", "s10", "s11",
        "t3", "t4", "t5", "t6"
    };

    private Register[] registers;
    private Register pc;

    public RegisterFile() {
        this.registers = new Register[NUM_REGISTERS];
        for(int i=0; i < REG_NAMES.length; i++) {
            this.registers[i] = new Register(i, 0, REG_NAMES[i], i != 0);
        }
        this.pc = new Register(32, 0, "PC", true);
    }

    public Register getRegister(String name) {
        for(int i=0; i < this.registers.length; i++) {
            String regName = this.registers[i].getName();
            if (regName.contains(name))
                return this.registers[i];
        }
        return null;
    }

    public Register getRegister(int number) {
        for(int i=0; i < this.registers.length; i++) {
            if (this.registers[i].getNumber() == number)
                return this.registers[i];
        }
        return null;
    }

    public Register getProgramCounter() {
        return this.pc;
    }

    public void setRegister(String name, int value) {
        Register reg = getRegister(name);
        if (reg != null)
            reg.setValue(value);
    }

    public void setProgramCounter(int value) {
        this.pc.setValue(value);
    }

    public void setStackPointer(int value) {
        setRegister("sp", value);
    }

    public void setGlobalPointer(int value) {
        setRegister("gp", value);
    }

    @Override
    public String toString() {
        String out = "";
        String newline = System.getProperty("line.separator");
        for(int i=0; i < NUM_REGISTERS; i++) {
            out += this.registers[i].toString() + newline;
        }
        out += newline + String.format(
            "PC  [%s]",
            Colorize.yellow(String.format("0x%08x", this.pc.getValue()))
        );
        return out;
    }

}