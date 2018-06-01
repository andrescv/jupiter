package vsim.riscv.isa.instructions.rtype;


public final class Sra extends RType {

    public Sra() {
        super(
            "sra",
            "sra rd, rs1, rs2",
            "set rd = rs1 >> rs2[0:4], arithmetic shift right"
        );
    }

    @Override
    protected int compute(int rs1, int rs2) {
        return rs1 >> (rs2 & 0x1f);
    }

}