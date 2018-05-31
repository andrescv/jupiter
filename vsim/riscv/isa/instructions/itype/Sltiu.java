package vsim.riscv.isa.instructions.itype;


public final class Sltiu extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        return (Integer.compareUnsigned(rs1, imm) < 0) ? 1 : 0;
    }

}