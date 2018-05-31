package vsim.riscv.isa.instructions.itype;


public final class Srai extends IType {

    @Override
    public int compute(int x, int y) {
        return x >> (y & 0x1f);
    }

}