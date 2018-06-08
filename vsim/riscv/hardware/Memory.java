package vsim.riscv.hardware;

import vsim.utils.Data;
import vsim.utils.Colorize;
import java.util.Hashtable;
import vsim.riscv.MemoryConfig;


public final class Memory {

  private Hashtable<Integer, Byte> memory;

  public Memory() {
    this.memory = new Hashtable<Integer, Byte>();
  }

  public void printMemory(int from) {
    System.out.println(this.getMemory(from));
  }

  public void storeByte(int address, int value) {
    this.memory.put(address, (byte)(value & Data.BYTE_MASK));
  }

  public void storeHalf(int address, int value) {
    this.storeByte(address, value);
    this.storeByte(address + Data.BYTE_LENGTH, value >> Data.BYTE_LENGTH_BITS);
  }

  public void storeWord(int address, int value) {
    this.storeHalf(address, value);
    this.storeHalf(address + Data.HALF_LENGTH, value >> Data.HALF_LENGTH_BITS);
  }

  public int loadByteUnsigned(int address) {
    if (!this.memory.containsKey(address))
      return 0x0;
    return ((int)this.memory.get(address)) & Data.BYTE_MASK;
  }

  public int loadByte(int address) {
    return Data.signExtendByte(this.loadByteUnsigned(address));
  }

  public int loadHalfUnsigned(int address) {
    int loByte = this.loadByteUnsigned(address);
    int hiByte = this.loadByteUnsigned(address + Data.BYTE_LENGTH);
    return (hiByte << Data.BYTE_LENGTH_BITS) | loByte;
  }

  public int loadHalf(int address) {
    return Data.signExtendHalf(this.loadHalfUnsigned(address));
  }

  public int loadWord(int address) {
    int loHalf = this.loadHalfUnsigned(address);
    int hiHalf = this.loadHalfUnsigned(address + Data.HALF_LENGTH);
    return (hiHalf << Data.HALF_LENGTH_BITS) | loHalf;
  }

  private String getMemory(int from) {
    // 12 rows of 4 words
    String newline = System.getProperty("line.separator");
    String header = "Value (+0) Value (+4) Value (+8) Value (+c)";
    String out = "             " + Colorize.red(header + newline);
    for (int i = 0; i < Data.WORD_LENGTH * 12; i++) {
        int address = from + i * Data.WORD_LENGTH;
        // include address
        if (i % Data.WORD_LENGTH == 0)
            out += String.format("[0x%08x]", address);
        // word content at this address
        String data = String.format("0x%08x", this.loadWord(address));
        out += " " + Colorize.blue(data);
        // next 4 words in other row
        if ((i + 1) % Data.WORD_LENGTH == 0)
            out += newline;
    }
    // right strip whitespace
    return out.replaceAll("\\s+$","");
  }

  @Override
  public String toString() {
    return getMemory(MemoryConfig.DATA_SEGMENT);
  }

}
