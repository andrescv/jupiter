package vsim.riscv.hardware;

import java.util.Hashtable;
import vsim.riscv.hardware.MemoryCell;


public class Memory {

    private int heapAddress;
    private Hashtable<Integer, MemoryCell> memory;

    public Memory() {
        this.memory = new Hashtable<Integer, MemoryCell>();
        this.heapAddress = 0;
    }

    private void createCell(int address) {

    }

}