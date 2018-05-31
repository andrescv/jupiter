package vsim.riscv.isa.instructions.btype;


public final class Beq extends BType {

    @Override
    public boolean comparison(int rs1, int rs2) {
        return rs1 == rs2;
    }

}