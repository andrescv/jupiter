/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package vsim.riscv;

import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import vsim.Settings;
import vsim.utils.Colorize;
import vsim.utils.Data;
import vsim.utils.IO;
import vsim.utils.Message;


/**
 * The class Memory is used to represent the RISC-V principal memory (RAM).
 */
public final class Memory {

  /** number of memory cells to show in the GUI application */
  private static final int ROWS = 32;

  /** start address to generate memory cells */
  private static int START = MemorySegments.STATIC_SEGMENT + (ROWS - 1) * Data.WORD_LENGTH;

  /** the only available instance of the Memory class */
  public static final Memory ram = new Memory();

  /** all allocated bytes */
  private HashMap<Integer, Byte> memory;

  /** memory snapshot */
  private HashMap<Integer, Byte> snap;

  /** heap snapshot */
  private int heapSnap;

  /** current memory diff */
  private HashMap<Integer, Byte> diff;

  /** memory cell list */
  private ObservableList<MemoryCell> cells;

  /**
   * Unique constructor that initializes a newly and empty Memory object.
   *
   * @see vsim.riscv.Register
   * @see vsim.riscv.RVIRegisterFile
   */
  private Memory() {
    this.memory = new HashMap<Integer, Byte>();
    this.diff = new HashMap<Integer, Byte>();
    this.heapSnap = -1;
    // create initial memory cells
    this.cells = FXCollections.observableArrayList();
    for (int i = Memory.START, j = 0; j < ROWS; i -= Data.WORD_LENGTH, j++)
      this.cells.add(new MemoryCell(i));
  }

  /**
   * This method clears all the allocated bytes of the memory.
   */
  public void reset() {
    // reset memory
    if (this.snap != null)
      this.memory = this.snap;
    else
      this.memory.clear();
    // reset heap segment pointer
    MemorySegments.HEAP_SEGMENT = this.heapSnap;
    // refresh memory cells
    if (Settings.GUI) {
      for (MemoryCell cell : this.cells)
        cell.update();
    }
  }

  /**
   * Saves a memory and heap snapshot to reset later.
   */
  @SuppressWarnings("unchecked")
  public void snapshot() {
    this.snap = (HashMap<Integer, Byte>) this.memory.clone();
    this.heapSnap = MemorySegments.HEAP_SEGMENT;
  }

  /**
   * This method pretty prints the memory.
   *
   * @param from start address
   * @param rows how many rows of 4 words you want to print
   */
  public void print(int from, int rows) {
    // n rows of 4 words
    String newline = System.getProperty("line.separator");
    String header = "Value (+0) Value (+4) Value (+8) Value (+c)";
    String out = "             " + Colorize.red(header + newline);
    for (int i = 0; i < Data.WORD_LENGTH * rows; i++) {
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
    IO.stdout.println(out.replaceAll("\\s+$", ""));
  }

  /**
   * This method stores a byte in memory at address given.
   *
   * @param address address where to store the byte
   * @param value the byte value
   */
  public void storeByte(int address, int value) {
    if (Memory.checkStore(address)) {
      if (value != 0)
        this.memory.put(address, (byte) (value & Data.BYTE_MASK));
      else
        this.memory.remove(address);
    }
    // refresh memory cells
    if (Settings.GUI) {
      for (MemoryCell cell : this.cells)
        cell.update();
    }
  }

  /**
   * This method stores a byte in memory at address given without checks.
   *
   * @param address address where to store the byte
   * @param value the byte value
   */
  private void privStoreByte(int address, int value) {
    if (value != 0)
      this.memory.put(address, (byte) (value & Data.BYTE_MASK));
    else
      this.memory.remove(address);
    // refresh memory cells
    if (Settings.GUI) {
      for (MemoryCell cell : this.cells)
        cell.update();
    }
  }

  /**
   * This method stores a half in memory at address given.
   *
   * @param address address where to store the half
   * @param value the half value
   */
  public void storeHalf(int address, int value) {
    if (Memory.checkStore(address)) {
      this.storeByte(address, value);
      this.storeByte(address + Data.BYTE_LENGTH, value >> Data.BYTE_LENGTH_BITS);
    }
  }

  /**
   * This method stores a word in memory at address given.
   *
   * @param address address where to store the word value
   * @param value the word value
   */
  public void storeWord(int address, int value) {
    if (Memory.checkStore(address)) {
      this.storeHalf(address, value);
      this.storeHalf(address + Data.HALF_LENGTH, value >> Data.HALF_LENGTH_BITS);
    }
  }

  /**
   * This method stores a word at the given address. This method should only be used in the linking process.
   *
   * @param address address where to store the word value
   * @param value the word to store
   */
  public void privStoreWord(int address, int value) {
    for (int i = 0; i < Data.WORD_LENGTH; i++)
      this.memory.put(address++, (byte) ((value >>> (i * Data.BYTE_LENGTH_BITS)) & Data.BYTE_MASK));
    // refresh memory cells
    if (Settings.GUI) {
      for (MemoryCell cell : this.cells)
        cell.update();
    }
  }

  /**
   * This method allocates bytes from heap and returns the next available address.
   *
   * @param bytes number of bytes to allocate
   * @return the nex available heap address or -1 if no more space.
   */
  public int allocateBytesFromHeap(int bytes) {
    int address = MemorySegments.HEAP_SEGMENT;
    // allocate bytes only if possible
    if ((MemorySegments.HEAP_SEGMENT + bytes) > MemorySegments.STACK_SEGMENT) {
      if (!Settings.QUIET)
        Message.warning("runtime: no more heap space");
      return -1;
    }
    // allocate bytes
    MemorySegments.HEAP_SEGMENT += bytes;
    // align to a word boundary (if necessary)
    MemorySegments.HEAP_SEGMENT = Data.alignToWordBoundary(MemorySegments.HEAP_SEGMENT);
    return address;
  }

  /**
   * This method loads a unsigned byte value from memory at address given with verifications.
   *
   * @param address address where to load the unsigned byte value
   * @return the unsigned byte value
   */
  public int loadByteUnsigned(int address) {
    if (Memory.checkLoad(address)) {
      if (!this.memory.containsKey(address))
        return 0x0;
      return ((int) this.memory.get(address)) & Data.BYTE_MASK;
    }
    return 0x0;
  }

  /**
   * This method loads a unsigned byte value from memory at address given without verifications.
   *
   * @param address address where to load the unsigned byte value
   * @return the unsigned byte value
   */
  public int privLoadByteUnsigned(int address) {
    if (!this.memory.containsKey(address))
      return 0x0;
    return ((int) this.memory.get(address)) & Data.BYTE_MASK;
  }

  /**
   * This method loads a byte value from memory at address given.
   *
   * @param address address where to load the byte value
   * @return the byte value
   */
  public int loadByte(int address) {
    if (Memory.checkLoad(address))
      return Data.signExtendByte(this.loadByteUnsigned(address));
    return 0x0;
  }

  /**
   * This method loads a unsigned half value from memory at address given.
   *
   * @param address address where to load the unsigned half value
   * @return the unsigned half value
   */
  public int loadHalfUnsigned(int address) {
    if (Memory.checkLoad(address)) {
      int loByte = this.loadByteUnsigned(address);
      int hiByte = this.loadByteUnsigned(address + Data.BYTE_LENGTH);
      return (hiByte << Data.BYTE_LENGTH_BITS) | loByte;
    }
    return 0x0;
  }

  /**
   * This method loads a half value from memory at address given.
   *
   * @param address address where to load the half value
   * @return the half value
   */
  public int loadHalf(int address) {
    if (Memory.checkLoad(address))
      return Data.signExtendHalf(this.loadHalfUnsigned(address));
    return 0x0;
  }

  /**
   * This method loads a word value from memory at address given.
   *
   * @param address address where to load the word value
   * @return the word value
   */
  public int loadWord(int address) {
    if (Memory.checkLoad(address)) {
      int loHalf = this.loadHalfUnsigned(address);
      int hiHalf = this.loadHalfUnsigned(address + Data.HALF_LENGTH);
      return (hiHalf << Data.HALF_LENGTH_BITS) | loHalf;
    }
    return 0x0;
  }

  /**
   * Gets the observable list of memory cells.
   *
   * @return observable list of memory cells
   */
  public ObservableList<MemoryCell> getCells() {
    return this.cells;
  }

  /**
   * Use this method to see 4 memory cells above.
   */
  public void up() {
    Memory.START += 16;
    this.updateMemoryCells();
  }

  /**
   * Use this method to see 4 memory cells below.
   */
  public void down() {
    Memory.START -= 16;
    this.updateMemoryCells();
  }

  /**
   * This method sets {@link vsim.riscv.Memory#START} equal to {@link vsim.riscv.MemorySegments#TEXT_SEGMENT_BEGIN}.
   */
  public void text() {
    Memory.START = MemorySegments.TEXT_SEGMENT_BEGIN + (Memory.ROWS - 1) * Data.WORD_LENGTH;
    this.updateMemoryCells();
  }

  /**
   * This method sets {@link vsim.riscv.Memory#START} equal to {@link vsim.riscv.MemorySegments#STATIC_SEGMENT}.
   */
  public void data() {
    Memory.START = MemorySegments.STATIC_SEGMENT + (Memory.ROWS - 1) * Data.WORD_LENGTH;
    this.updateMemoryCells();
  }

  /**
   * This method sets {@link vsim.riscv.Memory#START} equal to {@link vsim.riscv.MemorySegments#HEAP_SEGMENT}.
   */
  public void stack() {
    Memory.START = MemorySegments.STACK_SEGMENT;
    this.updateMemoryCells();
  }

  /**
   * This method sets {@link vsim.riscv.Memory#START} equal to {@link vsim.riscv.MemorySegments#HEAP_SEGMENT}.
   */
  public void heap() {
    Memory.START = MemorySegments.HEAP_SEGMENT + (Memory.ROWS - 1) * Data.WORD_LENGTH;
    this.updateMemoryCells();
  }

  /**
   * This method updates all memory cells according to {@link vsim.riscv.Memory#START}.
   */
  private void updateMemoryCells() {
    for (int i = Memory.START, j = 0; j < ROWS; i -= Data.WORD_LENGTH, j++)
      this.cells.get(j).setIntAddress(i);
  }

  /**
   * This method returns the current heap address.
   *
   * @return current heap address
   */
  public int getHeapPointer() {
    return MemorySegments.HEAP_SEGMENT;
  }

  /**
   * This method restores the heap segment to a given value.
   *
   * @param value the heap segment address value to use for restore
   */
  public void restoreHeap(int value) {
    MemorySegments.HEAP_SEGMENT = value;
  }

  /**
   * This method gets the current diff of the main memory.
   *
   * @return main memory diff
   */
  public HashMap<Integer, Byte> getDiff() {
    HashMap<Integer, Byte> diff = this.diff;
    this.diff = new HashMap<Integer, Byte>();
    return diff;
  }

  /**
   * This methods restores the main memory given a diff between states.
   *
   * @param diff diff between states of the main memory
   */
  public void restore(HashMap<Integer, Byte> diff) {
    for (Integer key : diff.keySet())
      this.privStoreByte(key, diff.get(key));
  }

  /**
   * This method checks if the address given is a valid store address.
   *
   * @param address the address to check
   * @return true if the address is valid, false otherwise.
   */
  public static boolean checkAddress(int address) {
    // reserved memory ?
    if (Data.inRange(address, MemorySegments.RESERVED_LOW_BEGIN, MemorySegments.RESERVED_LOW_END)
        || Data.inRange(address, MemorySegments.RESERVED_HIGH_BEGIN, MemorySegments.RESERVED_HIGH_END))
      return false;
    // text segment ?
    if (Data.inRange(address, MemorySegments.TEXT_SEGMENT_BEGIN, MemorySegments.TEXT_SEGMENT_END))
      return false;
    // read only segment ?
    if (MemorySegments.RODATA_SEGMENT_BEGIN != MemorySegments.RODATA_SEGMENT_END
        && Data.inRange(address, MemorySegments.RODATA_SEGMENT_BEGIN, MemorySegments.RODATA_SEGMENT_END))
      return false;
    // otherwise
    return true;
  }

  /**
   * This method checks if the address given is a valid store address and displays a warning message if the address is
   * invalid.
   *
   * @param address the address to check
   * @return true if the address is valid, false otherwise.
   */
  public static boolean checkStore(int address) {
    // reserved memory ?
    if (Data.inRange(address, MemorySegments.RESERVED_LOW_BEGIN, MemorySegments.RESERVED_LOW_END)
        || Data.inRange(address, MemorySegments.RESERVED_HIGH_BEGIN, MemorySegments.RESERVED_HIGH_END)) {
      if (!Settings.QUIET)
        Message.warning("runtime: trying to store a value in reserved memory (ignoring)");
      return false;
    }
    // text segment ?
    if (Data.inRange(address, MemorySegments.TEXT_SEGMENT_BEGIN, MemorySegments.TEXT_SEGMENT_END)) {
      if (!Settings.QUIET)
        Message.warning("runtime: trying to store a value in text segment (ignoring)");
      return false;
    }
    // read only segment ?
    if (MemorySegments.RODATA_SEGMENT_BEGIN != MemorySegments.RODATA_SEGMENT_END
        && Data.inRange(address, MemorySegments.RODATA_SEGMENT_BEGIN, MemorySegments.RODATA_SEGMENT_END)) {
      if (!Settings.QUIET)
        Message.warning("runtime: trying to store a value in read only segment (ignoring)");
      return false;
    }
    // otherwise
    return true;
  }

  /**
   * This method checks if the address given is a valid load address.
   *
   * @param address the address to check
   * @return true if the address is valid, false otherwise.
   */
  public static boolean checkLoad(int address) {
    // reserved memory ?
    if (Data.inRange(address, MemorySegments.RESERVED_LOW_BEGIN, MemorySegments.RESERVED_LOW_END)
        || Data.inRange(address, MemorySegments.RESERVED_HIGH_BEGIN, MemorySegments.RESERVED_HIGH_END)) {
      if (!Settings.QUIET)
        Message.warning("runtime: trying to load a value from reserved memory");
      return false;
    }
    // otherwise
    return true;
  }

}
