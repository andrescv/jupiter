package vsim.riscv.isa;

import java.io.File;
import java.util.ArrayList;
import java.lang.reflect.Modifier;
import vsim.riscv.isa.instructions.Format;
import vsim.riscv.isa.instructions.Instruction;


public final class InstructionSet {

    private static final String BTYPE = "vsim.riscv.isa.instructions.btype";
    private static final String ITYPE = "vsim.riscv.isa.instructions.itype";
    private static final String JTYPE = "vsim.riscv.isa.instructions.jtype";
    private static final String RTYPE = "vsim.riscv.isa.instructions.rtype";
    private static final String STYPE = "vsim.riscv.isa.instructions.stype";
    private static final String UTYPE = "vsim.riscv.isa.instructions.utype";

    private ArrayList<Instruction> isa;

    public InstructionSet() {
        this.isa = new ArrayList<Instruction>();
        this.addInstructions();
    }

    private void add(String pkg, String path) {
        for (File f: (new File(path)).listFiles()) {
            // construct class path
            String className = f.getName().replace(".class", "");
            String classPath = pkg + "." + className;
            try {
                Class cls = Class.forName(classPath);
                // only final classes
                if (!Instruction.class.isAssignableFrom(cls) ||
                    Modifier.isAbstract(cls.getModifiers()) ||
                    Modifier.isInterface(cls.getModifiers()))
                    continue;
                // add this new instruction to isa
                this.isa.add(
                    (Instruction) cls.newInstance()
                );
            } catch (Exception e) {
                System.err.println("Invalid instruction class: " + className);
                System.exit(-1);
            }
        }
    }

    private void addInstructions() {
        String[][] set = {
            {RTYPE, RTYPE.replace('.', File.separatorChar)},
            {ITYPE, ITYPE.replace('.', File.separatorChar)},
            {STYPE, STYPE.replace('.', File.separatorChar)},
            {BTYPE, BTYPE.replace('.', File.separatorChar)},
            {UTYPE, UTYPE.replace('.', File.separatorChar)},
            {JTYPE, JTYPE.replace('.', File.separatorChar)}
        };
        for (int i = 0; i < set.length; i++) {
            this.add(set[i][0], set[i][1]);
        }
        this.isa.trimToSize();
    }


    public int size() {
        return this.isa.size();
    }

    @Override
    public String toString() {
        String out = "";
        for (int i = 0; i < this.isa.size(); i++) {
            out += this.isa.get(i).toString();
            out += System.getProperty("line.separator");
        }
        return out.replaceAll("\\s+$","");
    }

}