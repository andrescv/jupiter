import static vsim.Globals.*;
import vsim.riscv.isa.InstructionSet;

public class VSim {

    public static void main(String[] args) {
        InstructionSet isa = new InstructionSet();
        System.out.println(isa);
    }

}