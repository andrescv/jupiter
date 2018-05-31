package vsim.riscv.isa.instructions.rtype;


public final class Sltu extends RType {

    @Override
    public int compute(int x, int y) {
        return (Integer.compareUnsigned(x, y) < 0) ? 1 : 0;
    }

}