package vsim.riscv.isa.instructions.rtype;


public final class Srl extends RType {

    @Override
    public int compute(int x, int y) {
        return x >>> (y & 0x1f);
    }

}