package vsim.riscv.isa.instructions.itype;


public final class Slli extends IType {

    public Slli() {
        super(
            "slli",
            "slli rd, rs1, imm",
            "set rd = rs1 << imm[0:4], logical shift left"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return rs1 << (imm & 0x1f);
    }

}