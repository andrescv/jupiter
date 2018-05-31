package vsim.riscv.isa.instructions.rtype;


public final class Sub extends RType {

    @Override
    public int compute(int x, int y) {
        return x - y;
    }

}