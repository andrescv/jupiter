package vsim.riscv.hardware;

import vsim.utils.Colorize;
import vsim.utils.ByteOffset;
import vsim.utils.HalfOffset;
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

    public int loadByte(ByteOffset offset) {
        return SignExtender.signExtendByte(loadByteUnsigned(offset));
    }

    public int loadHalfUnsigned(HalfOffset offset) {
        int shift = offset.ordinal() * SignExtender.HALF_LENGTH_BITS;
        int mask = SignExtender.HALF_MASK << shift;
        return ((this.data & mask) >> shift) & SignExtender.HALF_MASK;
    }

    public int loadHalf(HalfOffset offset) {
        return SignExtender.signExtendHalf(loadHalfUnsigned(offset));
    }

    public int loadWord() {
        return this.data;
    }

    public void storeByte(int value, ByteOffset offset) {
        int shift = offset.ordinal() * SignExtender.BYTE_LENGTH_BITS;
        int mask = SignExtender.BYTE_MASK << shift;
        value = (SignExtender.BYTE_MASK & value) << shift;
        this.data = (this.data & ~mask) | value;
    }

    public void storeHalf(int value, HalfOffset offset) {
        int shift = offset.ordinal() * SignExtender.HALF_LENGTH_BITS;
        int mask = SignExtender.HALF_MASK << shift;
        value = (SignExtender.HALF_MASK & value) << shift;
        this.data = (this.data & ~mask) | value;
    }

    public void storeWord(int value) {
        this.data = value;
    }

    @Override
    public String toString() {
        if (this.data != 0)
            return Colorize.blue(String.format("0x%08x", this.data));
        else
            return Colorize.yellow(String.format("0x%08x", this.data));
    }

}