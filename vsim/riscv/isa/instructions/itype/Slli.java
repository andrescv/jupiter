package vsim.riscv.isa.instructions.itype;


public final class Slli extends IType {

    @Override
    public int compute(int x, int y) {
        return x << (y & 0x1f);
    }

}