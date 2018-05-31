package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lhu extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadHalfUnsigned(rs1 + imm);
    }

}