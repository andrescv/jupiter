package vsim.riscv.isa.instructions.itype;

import vsim.Globals;
import vsim.riscv.hardware.Register;


public final class Jalr extends IType {

    @Override
    public int compute(int x, int y) {
        Register pc = Globals.regfile.getProgramCounter();
        int nextPc = pc.getValue() + 4;
        int result = x + y;
        // set the least-significant bit of the result to zero
        pc.setValue((result >> 1) << 1);
        return nextPc;
    }

}