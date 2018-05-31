package vsim.riscv.isa.instructions.btype;


public final class Bltu extends BType {

    @Override
    public boolean comparison(int x, int y) {
        return Integer.compareUnsigned(x, y) < 0;
    }

}