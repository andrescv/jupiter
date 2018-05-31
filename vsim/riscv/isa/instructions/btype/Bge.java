package vsim.riscv.isa.instructions.btype;


public final class Bge extends Branch {

    @Override
    public boolean comparison(int x, int y) {
        return x >= y;
    }

}