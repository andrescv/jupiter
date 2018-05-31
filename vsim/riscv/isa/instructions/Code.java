package vsim.riscv.isa.instructions;

import vsim.utils.Colorize;
import vsim.utils.SignExtender;


public final class Code {

    private int code;

    public Code(int code) {
        this.code = code;
    }

    public int getOpcode() {
        return this.code & 0x7f;
    }

    public int getRs1() {
        return ((this.code & (0x1f << 15)) >> 15) & 0x1f;
    }

    public int getRs2() {
        return ((this.code & (0x1f << 20)) >> 20) & 0x1f;
    }

    public int getRd() {
        return ((this.code & (0x1f << 7)) >> 7) & 0x1f;
    }

    public int getImm(Format f) {
        int imm = 0;

        if (f == Format.I) {
            imm = ((this.code & (0xfff << 20)) >> 20) & 0xfff;
            imm = SignExtender.signExtend(imm, 12);
        } else if (f == Format.S) {
            int imm5 = ((this.code & (0x1f << 7)) >> 7) & 0x1f;
            int imm7 = ((this.code & (0x7f << 25)) >> 25) & 0x7f;
            imm = SignExtender.signExtend((imm7 << 5) | imm5, 12);
        } else if (f == Format.B) {
            int imm1a = ((this.code & (0x1 << 7)) >> 7) & 0x1;
            int imm4 = ((this.code & (0xf << 8)) >> 8) & 0xf;
            int imm6 = ((this.code & (0x3f << 25)) >> 25) & 0x3f;
            int imm1b = ((this.code & (0x1 << 31)) >> 31) & 0x1;
            imm = (imm1b << 11 | imm1a << 10 | imm6 << 4 | imm4) << 1;
            imm = SignExtender.signExtend(imm, 13);
        } else if (f == Format.U) {
            imm = ((this.code & (0xfffff << 12)) >> 12) & 0xfffff;
            imm = SignExtender.signExtend(imm, 20);
        } else if (f == Format.J) {
            int imm10 = ((this.code & (0x3ff << 9)) >> 9) & 0x1;
            int imm1b = ((this.code & (0x1 << 19)) >> 19) & 0x1;
            int imm1a = ((this.code & (0x1 << 8)) >> 8) & 0x1;
            int imm8 = this.code & 0xff;
            imm = (imm1b << 19 | imm8 << 11 | imm1a << 10 | imm10) << 1;
            imm = SignExtender.signExtend(imm, 21);
        } else
            imm = 0;

        return imm;
    }

    @Override
    public String toString() {
        return Colorize.cyan(String.format("0x%08x", this.code));
    }

}