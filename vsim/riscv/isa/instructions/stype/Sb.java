package vsim.riscv.isa.instructions.stype;

import vsim.Globals;


public final class Sb extends SType {

    public Sb() {
        super(
            "sb",
            "sb rs2, imm(rs1)",
            "set memory[rs1 + imm] = rs2[0:7]"
        );
    }

    @Override
    protected void set(int rs1, int rs2, int imm) {
        Globals.memory.storeByte(rs1 + imm, rs2);
    }

}