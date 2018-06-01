package vsim.riscv.isa.instructions.itype;


public final class Xori extends IType {

    public Xori() {
        super(
            "xori",
            "xori rd, rs1, imm",
            "set rd = rs1 ^ imm, bitwise xor"
        );
    }

    @Override
    protected int compute(int rs1, int imm) {
        return rs1 ^ imm;
    }

}