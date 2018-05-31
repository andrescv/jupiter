package vsim.riscv.isa.instructions.btype;


public final class Bgeu extends BType {

    @Override
    public boolean comparison(int rs1, int rs2) {
        int cmp = Integer.compareUnsigned(rs1, rs2);
        return (cmp == 0) || (cmp > 0);
    }

}