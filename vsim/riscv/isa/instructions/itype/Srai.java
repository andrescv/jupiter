package vsim.riscv.isa.instructions.itype;


public final class Srai extends IType {

    public Srai() {
        super(
            "srai",
            "srai rd, rs1, imm",
            "set rd = rs1 >> imm[0:4], arithmetic shift right"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return rs1 >> (imm & 0x1f);
    }

}