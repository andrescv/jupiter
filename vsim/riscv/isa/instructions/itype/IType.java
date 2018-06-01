package vsim.riscv.isa.instructions.itype;

import vsim.Globals;
import vsim.riscv.hardware.Register;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.Format;
import vsim.riscv.isa.instructions.Instruction;


abstract class IType extends Instruction {

    protected IType(String mnemonic, String usage, String description) {
        super(Format.I, mnemonic, usage, description);
    }

    protected abstract int compute(int rs1, int imm);

    @Override
    public void execute(Code code) {
        Register rs1 = Globals.regfile.getRegister(code.getRs1());
        int imm = code.getImm(Format.I);
        Register rd = Globals.regfile.getRegister(code.getRd());
        rd.setValue(this.compute(rs1.getValue(), imm));
    }

}