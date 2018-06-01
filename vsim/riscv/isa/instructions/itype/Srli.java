package vsim.riscv.isa.instructions.itype;


public final class Srli extends IType {

    public Srli() {
        super(
            "srli",
            "srli rd, rs1, imm",
            "set rd = rs1 >> imm[0:4], logical shift right"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return rs1 >>> (imm & 0x1f);
    }

}