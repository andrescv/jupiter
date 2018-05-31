package vsim.riscv.isa.instructions.btype;


public final class Beq extends BType {

    @Override
    public boolean comparison(int x, int y) {
        return x == y;
    }

}