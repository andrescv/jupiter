package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lh extends IType {

    public Lh() {
        super(
            "lh",
            "lh rd, imm(rs1)",
            "set rd = memory[rs1 + imm]"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadHalf(rs1 + imm);
    }

}