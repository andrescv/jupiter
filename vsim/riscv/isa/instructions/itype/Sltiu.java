package vsim.riscv.isa.instructions.itype;


public final class Sltiu extends IType {

    public Sltiu() {
        super(
            "sltiu",
            "sltiu rd, rs1, imm",
            "set rd = 1 if rs1 < imm else 0, unsigned comparison"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0;
    }

}