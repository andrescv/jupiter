package vsim.riscv.isa.instructions.itype;


public final class Srai extends IType {

    @Override
    public int compute(int rs1, int imm) {
        return rs1 >> (imm & 0x1f);
    }

}