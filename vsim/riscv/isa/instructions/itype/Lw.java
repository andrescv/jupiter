package vsim.riscv.isa.instructions.itype;

import vsim.Globals;


public final class Lw extends IType {

    public Lw() {
        super(
            "lw",
            "lw rd, imm(rs1)",
            "set rd = memory[rs1 + imm]"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return Globals.memory.loadWord(rs1 + imm);
    }

}