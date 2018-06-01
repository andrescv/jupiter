package vsim.riscv.isa.instructions.jtype;

import vsim.Globals;
import vsim.riscv.hardware.Register;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.Format;
import vsim.riscv.isa.instructions.Instruction;


abstract class JType extends Instruction {

    protected JType(String mnemonic, String usage, String description) {
        super(Format.J, mnemonic, usage, description);
    }

    protected abstract int compute(int imm);

    @Override
    public void execute(Code code) {
        Register rd = Globals.regfile.getRegister(code.getRd());
        int imm = code.getImm(Format.J);
        rd.setValue(this.compute(imm));
    }

}