package vsim.riscv.isa.instructions.itype;

import vsim.Globals;
import vsim.riscv.hardware.Register;


public final class Jalr extends IType {

    @Override
    protected int compute(int rs1, int imm) {
        Register pc = Globals.regfile.getProgramCounter();
        int nextPc = pc.getValue() + 4;
        int result = rs1 + imm;
        // set the least-significant bit of the result to zero
        pc.setValue((result >> 1) << 1);
        return nextPc;
    }

}