package vsim.riscv.isa.instructions.itype;


public final class Slti extends IType {

    @Override
    public int compute(int x, int y) {
        return (x < y) ? 1 : 0;
    }

}