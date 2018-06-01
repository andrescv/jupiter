package vsim.riscv.isa.instructions.stype;

import vsim.Globals;


public final class Sw extends SType {

    public Sw() {
        super(
            "sw",
            "sw rs2, imm(rs1)",
            "set memory[rs1 + imm] = rs2"
        );
    }

    @Override
    protected void set(int rs1, int rs2, int imm) {
        Globals.memory.storeWord(rs1 + imm, rs2);
    }

}