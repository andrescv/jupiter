package vsim.riscv.isa.instructions.utype;

import vsim.Globals;
import vsim.riscv.hardware.Register;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.Format;
import vsim.riscv.isa.instructions.SimCode;


public abstract class UType extends SimCode {

    protected abstract int compute(int imm);

    @Override
    public void execute(Code code) {
        Register rd = Globals.regfile.getRegister(code.getRd());
        int imm = code.getImm(Format.U);
        rd.setValue(this.compute(imm));
    }

}