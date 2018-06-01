package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lb extends IType {

    public Lb() {
        super(
            "lb",
            "lb rd, imm(rs1)",
            "set rd = memory[rs1 + imm]"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadByte(rs1 + imm);
    }

}