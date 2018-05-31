package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lb extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadByte(rs1 + imm);
    }

}