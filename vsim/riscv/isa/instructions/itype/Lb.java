package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lb extends IType {

    @Override
    public int compute(int x, int y) {
        return Globals.memory.loadByte(x + y);
    }

}