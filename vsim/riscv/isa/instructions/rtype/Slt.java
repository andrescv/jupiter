package vsim.riscv.isa.instructions.rtype;


public final class Slt extends RType {

    public Slt() {
        super(
            "slt",
            "slt rd, rs1, rs2",
            "set rd = 1 if rs1 < rs2 else 0, signed comparison"
        );
    }

    @Override
    protected int compute(int rs1, int rs2) {
        return (rs1 < rs2) ? 1 : 0;
    }

}