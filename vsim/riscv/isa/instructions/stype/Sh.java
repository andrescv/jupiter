package vsim.riscv.isa.instructions.stype;

import vsim.Globals;


public final class Sh extends SType {

    public Sh() {
        super(
            "sh",
            "sh rs2, imm(rs1)",
            "set memory[rs1 + imm] = rs2[0:15]"
        );
    }

    @Override
    protected void set(int rs1, int rs2, int imm) {
        Globals.memory.storeHalf(rs1 + imm, rs2);
    }

}