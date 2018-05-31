package vsim.riscv.isa.instructions.itype;


public final class Sltiu extends IType {

    @Override
    public int compute(int x, int y) {
        return (Integer.compareUnsigned(x, y) < 0) ? 1 : 0;
    }

}