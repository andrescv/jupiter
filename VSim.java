import static vsim.Globals.*;
import vsim.riscv.isa.instructions.itype.*;
import vsim.riscv.isa.instructions.Code;
import vsim.riscv.isa.instructions.SimCode;


public class VSim {

    public static void main(String[] args) {
        SimCode lwcode = new Lw();

        Code c2 = new Code(0x00002303);

        memory.storeWord(0xbacacafe, 0x0);

        lwcode.execute(c2);

        System.out.println(memory);
        System.out.println(regfile);
    }

}