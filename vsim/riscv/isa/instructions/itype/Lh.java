package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lh extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadHalf(rs1 + imm);
    }

}