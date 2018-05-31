package vsim.riscv.isa.instructions.stype;

import vsim.Globals;


public final class Sb extends SType {

    @Override
    protected void set(int rs1, int rs2, int imm) {
        Globals.memory.storeByte(rs1 + imm, rs2);
    }

}