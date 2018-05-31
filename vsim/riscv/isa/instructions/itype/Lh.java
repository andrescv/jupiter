package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lh extends IType {

    @Override
    public int compute(int x, int y) {
        return Globals.memory.loadHalf(x + y);
    }

}