package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lw extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadWord(rs1 + imm);
    }

}