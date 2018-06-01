package vsim.riscv.isa.instructions.rtype;


public final class Sll extends RType {

    public Sll() {
        super(
            "sll",
            "sll rd, rs1, rs2",
            "set rd = rs1 << rs2[0:4], logical shift left"
        );
    }

    @Override
    protected int compute(int rs1, int rs2) {
        return rs1 << (rs2 & 0x1f);
    }

}