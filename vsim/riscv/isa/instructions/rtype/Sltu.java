package vsim.riscv.isa.instructions.rtype;

import vsim.Globals;
import vsim.riscv.hardware.Register;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.SimCode;


public final class Sltu extends SimCode {

    @Override
    public void execute(Code code) {
        Register rs1 = Globals.regfile.getRegister(code.getRs1());
        Register rs2 = Globals.regfile.getRegister(code.getRs2());
        Register rd = Globals.regfile.getRegister(code.getRd());
        int x = rs1.getValue();
        int y = rs2.getValue();
        if (Integer.compareUnsigned(x, y) < 0)
            rd.setValue(1);
        else
            rd.setValue(0);
    }

}