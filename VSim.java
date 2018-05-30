import vsim.utils.*;
import vsim.riscv.hardware.MemoryCell;


public class VSim {

    public static void main(String[] args) {
        MemoryCell cell = new MemoryCell(0xbaddcafe);

        // signed
        System.out.println(String.format("0x%08x", cell.loadByte(ByteOffset.FIRST)));
        System.out.println(String.format("0x%08x", cell.loadByte(ByteOffset.SECOND)));
        System.out.println(String.format("0x%08x", cell.loadByte(ByteOffset.THIRD)));
        System.out.println(String.format("0x%08x", cell.loadByte(ByteOffset.FOURTH)));
        // unsigned
        System.out.println(String.format("0x%08x", cell.loadByteUnsigned(ByteOffset.FIRST)));
        System.out.println(String.format("0x%08x", cell.loadByteUnsigned(ByteOffset.SECOND)));
        System.out.println(String.format("0x%08x", cell.loadByteUnsigned(ByteOffset.THIRD)));
        System.out.println(String.format("0x%08x", cell.loadByteUnsigned(ByteOffset.FOURTH)));

    }

}