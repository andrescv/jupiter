package vsim.riscv.hardware;

import vsim.utils.Data;
import vsim.utils.Colorize;
import java.util.Hashtable;


public final class Memory {

  private Hashtable<Long, Integer> memory;

  public Memory() {
    this.memory = new Hashtable<Long, Integer>();
  }

  public void printMemory(long from, long rows) {
    System.out.println(this.getMemory(from, rows));
  }

  private void allocateNewAddress(long address) {
      if (!this.memory.containsKey(address))
        this.memory.put(address, 0x00000000);
  }

  public void storeByte(int value, long address) {
    long wordAddress = address / Data.WORD_LENGTH * Data.WORD_LENGTH;
    this.allocateNewAddress(wordAddress);
    int offset = (int)(address - wordAddress);
    int data = this.memory.get(wordAddress);
    this.memory.put(wordAddress, Data.setByte(data, value, offset));
  }

  public void storeHalf(int value, long address) {
    this.storeByte(value, address);
    this.storeByte(value >> Data.BYTE_LENGTH_BITS, address + Data.BYTE_LENGTH);
  }

  public void storeWord(int value, long address) {
    this.storeHalf(value, address);
    this.storeHalf(value >> Data.HALF_LENGTH_BITS, address + Data.HALF_LENGTH);
  }

  public int loadByteUnsigned(long address) {
    long wordAddress = address / Data.WORD_LENGTH * Data.WORD_LENGTH;
    if (!this.memory.containsKey(wordAddress))
        return 0x0;
    int offset = (int)(address - wordAddress);
    int word = this.memory.get(wordAddress);
    return Data.getByteUnsigned(word, offset);
  }

  public int loadByte(long address) {
    return Data.signExtendByte(this.loadByteUnsigned(address));
  }

  public int loadHalfUnsigned(long address) {
    int loByte = this.loadByteUnsigned(address);
    int hiByte = this.loadByteUnsigned(address + Data.BYTE_LENGTH);
    return (hiByte << Data.BYTE_LENGTH_BITS) | loByte;
  }

  public int loadHalf(long address) {
    return Data.signExtendHalf(this.loadHalfUnsigned(address));
  }

  public int loadWord(long address) {
    int loHalf = this.loadHalfUnsigned(address);
    int hiHalf = this.loadHalfUnsigned(address + Data.HALF_LENGTH);
    return (hiHalf << Data.HALF_LENGTH_BITS) | loHalf;
  }

  private String getMemory(long from, long rows) {
    long to = from + Data.WORD_LENGTH * rows * 4;
    String newline = System.getProperty("line.separator");
    String header = "Value (+0) Value (+4) Value (+8) Value (+c)";
    String out = "             " + Colorize.red(header + newline);
    for (long i = 0, addr = from; addr < to; addr += Data.WORD_LENGTH, i++) {
        // address
        if (i % Data.WORD_LENGTH == 0)
            out += String.format("[0x%08x]", addr);
        // cell content
        String data = String.format("0x%08x", this.loadWord(addr));
        out += " " + Colorize.blue(data);
        // next 4 words
        if ((i + 1) % Data.WORD_LENGTH == 0)
            out += newline;
    }
    return out.replaceAll("\\s+$","");
  }

  @Override
  public String toString() {
    return getMemory(MemoryConfig.DATA_SEGMENT, 12);
  }

}
