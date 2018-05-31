package vsim.riscv.hardware;

import vsim.utils.Colorize;
import vsim.utils.SignExtender;


public final class MemoryCell {

    private int data;

    public MemoryCell() {
        this(0);
    }

    public MemoryCell(int value) {
        this.data = value;
    }

    public int loadByteUnsigned(ByteOffset offset) {
        int shift = offset.ordinal() * SignExtender.BYTE_LENGTH_BITS;
        int mask = SignExtender.BYTE_MASK << shift;
        return ((this.data & mask) >> shift) & SignExtender.BYTE_MASK;
    }

    public void storeByte(int value, ByteOffset offset) {
        int shift = offset.ordinal() * SignExtender.BYTE_LENGTH_BITS;
        int mask = SignExtender.BYTE_MASK << shift;
        value = (SignExtender.BYTE_MASK & value) << shift;
        this.data = (this.data & ~mask) | value;
    }

    @Override
    public String toString() {
        if (this.data != 0)
            return Colorize.blue(String.format("0x%08x", this.data));
        else
            return Colorize.yellow(String.format("0x%08x", this.data));
    }

}