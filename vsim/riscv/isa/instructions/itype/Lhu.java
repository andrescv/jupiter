package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lhu extends IType {

    public Lhu() {
        super(
            "lhu",
            "lhu rd, imm(rs1)",
            "set rd = memory[rs1 + imm]"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadHalfUnsigned(rs1 + imm);
    }

}