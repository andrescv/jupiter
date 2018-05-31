package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lbu extends IType {

    @Override
    public int compute(int x, int y) {
        return Globals.memory.loadByteUnsigned(x + y);
    }

}