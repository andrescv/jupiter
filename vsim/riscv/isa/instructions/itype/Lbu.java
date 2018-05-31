package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lbu extends IType {

    @Override
    public int compute(int rs1, int imm) {
        return Globals.memory.loadByteUnsigned(rs1 + imm);
    }

}