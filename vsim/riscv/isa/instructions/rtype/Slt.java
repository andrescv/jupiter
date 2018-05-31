package vsim.riscv.isa.instructions.rtype;


public final class Slt extends RType {

    @Override
    public int compute(int x, int y) {
        return (x < y) ? 1 : 0;
    }

}