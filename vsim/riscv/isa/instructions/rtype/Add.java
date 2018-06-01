package vsim.riscv.isa.instructions.rtype;


public final class Add extends RType {

    public Add() {
        super(
            "add",
            "add rd, rs1, rs2",
            "set rd = rs1 + rs2, overflow is ignored"
        );
    }

    @Override
    protected int compute(int rs1, int rs2) {
        return rs1 + rs2;
    }

}