package vsim.riscv.isa.instructions.btype;


public final class Beq extends Branch {

    @Override
    public boolean comparison(int x, int y) {
        System.out.println(x == y);
        return x == y;
    }

}