package vsim.riscv.isa.instructions.utype;

import vsim.Globals;
import vsim.riscv.hardware.Register;


public final class Auipc extends UType {

    public Auipc() {
        super(
            "auipc",
            "auipc rd, imm",
            "set rd = (imm << 20) + pc"
        );
    }

    @Override
    protected int compute(int imm) {
        Register pc = Globals.regfile.getProgramCounter();
        return ((imm << 20) & 0xfffff000) + pc.getValue();
    }

}