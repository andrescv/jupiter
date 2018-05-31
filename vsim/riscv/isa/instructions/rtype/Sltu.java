package vsim.riscv.isa.instructions.rtype;


public final class Sltu extends RType {

    @Override
    protected int compute(int rs1, int rs2) {
        return (Integer.compareUnsigned(rs1, rs2) < 0) ? 1 : 0;
    }

}