package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lw extends IType {

    @Override
    public int compute(int x, int y) {
        return Globals.memory.loadWord(x + y);
    }

}