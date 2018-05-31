package vsim.riscv.isa.instructions.stype;

import vsim.Globals;


public final class Sw extends SType {

    @Override
    public void set(int rs1, int rs2, int imm) {
        Globals.memory.storeWord(rs1 + imm, rs2);
    }

}