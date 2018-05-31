package vsim.riscv.isa.instructions.btype;


public final class Bltu extends BType {

    @Override
    protected boolean comparison(int rs1, int rs2) {
        return Integer.compareUnsigned(rs1, rs2) < 0;
    }

}