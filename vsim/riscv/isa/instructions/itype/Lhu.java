package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lhu extends IType {

    @Override
    public int compute(int x, int y) {
        return Globals.memory.loadHalfUnsigned(x + y);
    }

}