package vsim.riscv.isa.instructions.itype;


public final class Xori extends IType {

    @Override
    public int compute(int x, int y) {
        return x ^ y;
    }

}