package vsim.riscv.isa.instructions.btype;


public final class Bgeu extends BType {

    @Override
    public boolean comparison(int x, int y) {
        int cmp = Integer.compareUnsigned(x, y);
        return (cmp == 0) || (cmp > 0);
    }

}