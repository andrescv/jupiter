package vsim.riscv.isa.instructions.itype;


public final class Andi extends IType {

    @Override
    public int compute(int x, int y) {
        return x & y;
    }

}