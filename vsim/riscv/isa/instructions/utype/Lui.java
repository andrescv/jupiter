package vsim.riscv.isa.instructions.utype;


public final class Lui extends UType {

    public Lui() {
        super(
            "lui",
            "lui rd, imm",
            "set rd = (imm << 20)"
        );
    }

    @Override
    protected int compute(int imm) {
        return (imm << 20) & 0xfffff000;
    }

}