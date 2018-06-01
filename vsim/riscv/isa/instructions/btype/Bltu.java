package vsim.riscv.isa.instructions.btype;


public final class Bltu extends BType {

    public Bltu() {
        super(
            "bltu",
            "bltu rs1, rs2, imm",
            "set pc = pc + imm if rs1 < rs2, unsigned comparison"
        );
    }

    @Override
    protected boolean comparison(int rs1, int rs2) {
        return Integer.compareUnsigned(rs1, rs2) < 0;
    }

}