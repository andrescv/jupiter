package vsim.riscv.hardware;

import java.util.Hashtable;
import vsim.utils.ByteOffset;
import vsim.utils.Colorize;
import vsim.utils.SignExtender;
import vsim.riscv.hardware.MemoryConfig;
import static vsim.utils.SignExtender.HALF_LENGTH;
import static vsim.utils.SignExtender.WORD_LENGTH;


public class Memory {

    private static final long MASK = 0xffffffffL;

    private Hashtable<Long, MemoryCell> mem;
    private long heapAddress;

    public Memory() {
        this.mem = new Hashtable<Long, MemoryCell>();
        this.heapAddress = MemoryConfig.HEAP_ADDRESS;
    }

    private void createMemoryCell(long address) {
        address &= SignExtender.WORD_MASK;
        if (Memory.isWordAligned(address)) {
            if (!this.mem.containsKey(address))
                this.mem.put(address, new MemoryCell());
        }
    }

    public void storeByte(int value, long address) {
        address &= MASK;
        long wordAddress = address / WORD_LENGTH * WORD_LENGTH;
        this.createMemoryCell(wordAddress);
        int offsetIndex = (int)(address - wordAddress);
        ByteOffset offset = ByteOffset.values()[offsetIndex];
        this.mem.get(wordAddress).storeByte(value, offset);
    }

    public void storeHalf(int value, long address) {
        this.storeByte(value, address);
        this.storeByte(value >> SignExtender.BYTE_LENGTH_BITS, address + 1);
    }

    public void storeWord(int value, long address) {
        this.storeHalf(value, address);
        this.storeHalf(value >> SignExtender.HALF_LENGTH_BITS, address + 2);
    }

    public long allocateBytesFromHeap(int numBytes) {
        long result = -1;
        if (numBytes > 0) {
            result = this.heapAddress;
            this.heapAddress += numBytes;
            if (this.heapAddress >= MemoryConfig.DATA_LIMIT_ADDRESS) {

            }
        }
        return result;
    }

    public int loadByteUnsigned(long address) {
        address &= MASK;
        long wordAddress = address / WORD_LENGTH * WORD_LENGTH;
        if (!this.mem.containsKey(wordAddress))
            return 0x0;
        int offsetIndex = (int)(address - wordAddress);
        ByteOffset offset = ByteOffset.values()[offsetIndex];
        return this.mem.get(wordAddress).loadByteUnsigned(offset);
    }

    public int loadByte(long address) {
        return SignExtender.signExtendByte(this.loadByteUnsigned(address));
    }

    public int loadHalfUnsigned(long address) {
        int loByte = this.loadByteUnsigned(address);
        int hiByte = this.loadByteUnsigned(address + 1);
        return (hiByte << SignExtender.BYTE_LENGTH_BITS) | loByte;
    }

    public int loadHalf(long address) {
        return SignExtender.signExtendHalf(this.loadHalfUnsigned(address));
    }

    public int loadWord(long address) {
        int loHalf = this.loadHalfUnsigned(address);
        int hiHalf = this.loadHalfUnsigned(address + 2);
        return (hiHalf << SignExtender.HALF_LENGTH_BITS) | loHalf;
    }

    public String getMemoryStr(long from, int rows) {
        from &= MASK;
        long to = from + WORD_LENGTH * rows * 4;
        String header = "Value (+0) Value (+4) Value (+8) Value (+c)";
        String out = "             " + Colorize.cyan(header + "\n");
        for(long i=0, addr=from; addr < to; addr += WORD_LENGTH, i++) {
            // address
            if (i % WORD_LENGTH == 0)
                out += String.format("[0x%08x]", addr);
            // cell content
            if (this.mem.containsKey(addr))
                out += " " + this.mem.get(addr).toString();
            else
                out += " " + Colorize.yellow("0x00000000");
            // next 4 words
            if ((i + 1) % WORD_LENGTH == 0)
                out += System.getProperty("line.separator");
        }
        return out.replaceAll("\\s+$","");
    }

    @Override
    public String toString() {
        return this.getMemoryStr(0, 12);
    }

    public static boolean isWordAligned(long address) {
        return (address % WORD_LENGTH) == 0;
    }

    public static boolean isHalfAligned(long address) {
        return (address % HALF_LENGTH) == 0;
    }

    public static long alignToWordBoundary(long address) {
        if (!Memory.isWordAligned(address))
            address += (WORD_LENGTH - (address % WORD_LENGTH));
        return address;
    }

    public static long alignToHalfBoundary(long address) {
        if (!Memory.isHalfAligned(address))
            address += (HALF_LENGTH - (address % HALF_LENGTH));
        return address;
    }

    public static boolean inRange(long address, long base, long limit) {
        return address >= base && address <= limit;
    }

}