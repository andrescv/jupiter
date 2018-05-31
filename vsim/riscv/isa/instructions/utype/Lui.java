package vsim.riscv.isa.instructions.utype;


public final class Lui extends UType {

    @Override
    public int compute(int imm) {
        return (imm << 20) & 0xfffff000;
    }

}