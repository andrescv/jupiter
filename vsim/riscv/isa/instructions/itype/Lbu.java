package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lbu extends IType {

    public Lbu() {
        super(
            "lbu",
            "lbu rd, imm(rs1)",
            "set rd = memory[rs1 + imm]"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadByteUnsigned(rs1 + imm);
    }

}