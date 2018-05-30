package vsim.riscv.hardware;

import java.util.Hashtable;
import vsim.utils.Colorize;


public class RegisterFile {

    public static final String[] REGISTER_NAMES = {
        "zero", "ra", "sp", "gp",
        "tp", "t0", "t1", "t2",
        "s0/fp", "s1", "a0", "a1",
        "a2", "a3", "a4", "a5",
        "a6", "a7", "s2", "s3",
        "s4", "s5", "s6", "s7",
        "s8", "s9", "s10", "s11",
        "t3", "t4", "t5", "t6"
    };

    private Hashtable<String, Register> regfile;
    private Register pc;

    public RegisterFile() {
        this.regfile = new Hashtable<String, Register>();
        for (int i = 0; i < REGISTER_NAMES.length; i++) {
            Register reg = new Register(i, 0, i != 0);
            String name = REGISTER_NAMES[i];
            if (name.contains("/")) {
                String[] names = name.split("/");
                for (int j = 0; j < names.length; j++)
                    this.regfile.put(names[j], reg);
            } else
                this.regfile.put(name, reg);
        }
        this.pc = new Register(-1, 0, true);
    }

    public Register getRegister(String name) {
        return this.regfile.get(name.toLowerCase());
    }

    public Register getRegister(int number) {
        if (number >= 0 && number < REGISTER_NAMES.length) {
            String name = REGISTER_NAMES[number];
            if (name.contains("/"))
                name = name.split("/")[0];
            return this.getRegister(name);
        }
        return null;
    }

    public Register getProgramCounter() {
        return this.pc;
    }

    public Register getStackPointer() {
        return this.getRegister("sp");
    }

    public Register getGlobalPointer() {
        return this.getRegister("gp");
    }

    public void setProgramCounter(int value) {
        this.pc.setValue(value);
    }

    public void setStackPointer(int value) {
        this.getStackPointer().setValue(value);
    }

    public void setGlobalPointer(int value) {
        this.getGlobalPointer().setValue(value);
    }

    @Override
    public String toString() {
        String out = "";
        String regfmt = "%s%s [%s] (%s)";
        String space = " ";
        String newline = System.getProperty("line.separator");

        for(int i=0; i < REGISTER_NAMES.length; i++) {
            Register reg = this.getRegister(i);
            if (i >= 10)
                space = "";
            out += String.format(
                regfmt,
                Colorize.green(String.format("X%d", reg.getNumber())),
                space,
                Colorize.red(String.format("0x%08x", reg.getValue())),
                Colorize.cyan(REGISTER_NAMES[i])
            ) + newline;
        }

        out += newline + String.format(
            "PC  [%s]",
            Colorize.yellow(String.format("0x%08x", this.pc.getValue()))
        );

        return out;
    }

}