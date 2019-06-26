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

package vsim.riscv.hardware;

import java.util.HashMap;

import vsim.exceptions.InvalidAddressException;
import vsim.utils.Data;
import vsim.utils.IO;


/** Represents the main memory (RAM). */
public final class Memory {

  /** address where text segment ends */
  private int textEnd;
  /** address where rodata segment starts */
  private int rodataStart;
  /** address where rodata segment ends */
  private int rodataEnd;

  /** current heap pointer */
  private int heap;
  /** heap start pointer */
  private int heapStart;

  /** all allocated bytes */
  private final HashMap<Integer, Byte> memory;
  /** memory diff */
  private HashMap<Integer, Byte> diff;

  /** Creates a new main memory (RAM). */
  public Memory() {
    memory = new HashMap<>();
    diff = new HashMap<>();
  }

  /** Clears all the allocated bytes from memory. */
  public void reset() {
    memory.clear();
    diff.clear();
  }

  /**
   * Sets memory layout.
   *
   * @param textEnd where text segment ends
   * @param rodataStart where rodata segment starts
   * @param rodataEnd where rodata segment ends
   * @param heapStart where heap segment starts
   */
  public void setLayout(int textEnd, int rodataStart, int rodataEnd, int heapStart) {
    this.textEnd = textEnd;
    this.rodataStart = rodataStart;
    this.rodataEnd = rodataEnd;
    this.heapStart = heapStart;
    heap = heapStart;
  }

  /**
   * Pretty prints the memory.
   *
   * @param from start address
   * @param rows how many rows of 4 words to print
   */
  public void print(int from, int rows) {
    // n rows of 4 words
    String header = "Value (+0) Value (+4) Value (+8) Value (+c)";
    String out = "             " + header + Data.EOL;
    for (int i = 0; i < Data.WORD_LENGTH * rows; i++) {
      int address = from + i * Data.WORD_LENGTH;
      // include address
      if (i % Data.WORD_LENGTH == 0) {
        out += String.format("[0x%08x]", address);
      }
      // word content at this address
      String data = String.format("0x%08x", privLoadWord(address));
      out += " " + data;
      // next 4 words in other row
      if ((i + 1) % Data.WORD_LENGTH == 0) {
        out += Data.EOL;
      }
    }
    // right strip whitespace
    IO.stdout().println(out.replaceAll("\\s+$", ""));
  }

  /**
   * Sets current heap pointer.
   *
   * @param address new heap pointer address
   */
  public void setHeapPointer(int address) {
    heap = address;
  }

  /**
   * Restores the main memory given a diff between states.
   *
   * @param diff diff between states
   */
  public void restore(HashMap<Integer, Byte> diff) {
    for (Integer key : diff.keySet()) {
      privStoreByte(key, diff.get(key));
    }
  }

  /**
   * Stores a byte at the given address without checks.
   *
   * @param address address where the byte value will be stored
   * @param value the byte value to store
   */
  public void privStoreByte(int address, int value) {
    if (memory.containsKey(address) && (memory.get(address) != ((byte) (value & Data.BYTE_MASK)))) {
      diff.put(address, memory.get(address));
    }
    memory.put(address, (byte) (value & Data.BYTE_MASK));
  }

  /**
   * Stores a byte at the given address.
   *
   * @param address address where the byte value will be stored
   * @param value the byte value to store
   * @throws InvalidAddressException if the address is invalid
   */
  public void storeByte(int address, int value) throws InvalidAddressException {
    if (checkAddress(address, false)) {
      privStoreByte(address, value);
    } else {
      throw new InvalidAddressException(address, false);
    }
  }

  /**
   * Stores a half at the given address.
   *
   * @param address address where the half value will be stored
   * @param value the half value to store
   * @throws InvalidAddressException if the address is invalid
   */
  public void storeHalf(int address, int value) throws InvalidAddressException {
    storeByte(address, value);
    storeByte(address + Data.BYTE_LENGTH, value >> Data.BYTE_LENGTH_BITS);
  }

  /**
   * Stores a half at the given address without checks.
   *
   * @param address address where the half value will be stored
   * @param value the half value to store
   */
  public void privStoreHalf(int address, int value) {
    privStoreByte(address, value);
    privStoreByte(address + Data.BYTE_LENGTH, value >> Data.BYTE_LENGTH_BITS);
  }

  /**
   * Stores a word at the given address.
   *
   * @param address address where the word value will be stored
   * @param value the word value to store
   * @throws InvalidAddressException if the address is invalid
   */
  public void storeWord(int address, int value) throws InvalidAddressException {
    storeHalf(address, value);
    storeHalf(address + Data.HALF_LENGTH, value >> Data.HALF_LENGTH_BITS);
  }

  /**
   * Stores a word at the given address without checks.
   *
   * @param address address where the word value will be stored
   * @param value the word value to store
   */
  public void privStoreWord(int address, int value) {
    privStoreHalf(address, value);
    privStoreHalf(address + Data.HALF_LENGTH, value >> Data.HALF_LENGTH_BITS);
  }

  /**
   * Loads an unsigned byte from memory at the given address without checks.
   *
   * @param address memory address where the unsigned byte value will be loaded
   * @return the unsigned byte value from memory
   */
  public int privLoadByteUnsigned(int address) {
    if (!memory.containsKey(address)) {
      return 0x0;
    }
    return ((int) memory.get(address)) & Data.BYTE_MASK;
  }

  /**
   * Loads unsigned byte from memory at the given address.
   *
   * @param address memory address where the unsigned byte value will be loaded
   * @return the unsigned byte value from memory
   * @throws InvalidAddressException if the address is invalid
   */
  public int loadByteUnsigned(int address) throws InvalidAddressException {
    if (checkAddress(address, true)) {
      return privLoadByteUnsigned(address);
    } else
      throw new InvalidAddressException(address, true);
  }

  /**
   * Loads byte from memory at the given address.
   *
   * @param address memory address where the byte value will be loaded
   * @return the byte value from memory
   * @throws InvalidAddressException if the address is invalid
   */
  public int loadByte(int address) throws InvalidAddressException {
    return Data.signExtendByte(loadByteUnsigned(address));
  }

  /**
   * Loads a byte from memory at the given address without checks.
   *
   * @param address memory address where the byte value will be loaded
   * @return the byte value from memory
   */
  public int privLoadByte(int address) {
    return Data.signExtendByte(privLoadByteUnsigned(address));
  }

  /**
   * Loads an unsgined half from memory at the given address.
   *
   * @param address memory address where the unsigned half value will be loaded
   * @return the unsigned half value from memory
   * @throws InvalidAddressException if the address is invalid
   */
  public int loadHalfUnsigned(int address) throws InvalidAddressException {
    int loByte = loadByteUnsigned(address);
    int hiByte = loadByteUnsigned(address + Data.BYTE_LENGTH);
    return (hiByte << Data.BYTE_LENGTH_BITS) | loByte;
  }

  /**
   * Loads an unsigned half from memory at the given address without checks.
   *
   * @param address memory address where the unsigned half value will be loaded
   * @return the unsgined half value from memory
   */
  public int privLoadHalfUnsigned(int address) {
    int loByte = privLoadByteUnsigned(address);
    int hiByte = privLoadByteUnsigned(address + Data.BYTE_LENGTH);
    return (hiByte << Data.BYTE_LENGTH_BITS) | loByte;
  }

  /**
   * Loads a half from memory at the given address.
   *
   * @param address memory address where the half value will be loaded
   * @return the half value from memory
   * @throws InvalidAddressException if the address is invalid
   */
  public int loadHalf(int address) throws InvalidAddressException {
    return Data.signExtendHalf(loadHalfUnsigned(address));
  }

  /**
   * Loads a half from memory at the given address without checks.
   *
   * @param address memory address where the half value will be loaded
   * @return the half value from memory
   */
  public int privLoadHalf(int address) {
    return Data.signExtendHalf(privLoadHalfUnsigned(address));
  }

  /**
   * Loads a word from memory at the given address.
   *
   * @param address memory address where the word value will be loaded
   * @return the word value from memory
   * @throws InvalidAddressException if the address is invalid
   */
  public int loadWord(int address) throws InvalidAddressException {
    int loHalf = loadHalfUnsigned(address);
    int hiHalf = loadHalfUnsigned(address + Data.HALF_LENGTH);
    return (hiHalf << Data.HALF_LENGTH_BITS) | loHalf;
  }

  /**
   * Loads a word from memory at the given address without checks.
   *
   * @param address memory address where the word value will be loaded
   * @return the word value from memory
   */
  public int privLoadWord(int address) {
    int loHalf = privLoadHalfUnsigned(address);
    int hiHalf = privLoadHalfUnsigned(address + Data.HALF_LENGTH);
    return (hiHalf << Data.HALF_LENGTH_BITS) | loHalf;
  }

  /**
   * Allocates bytes from heap and returns a pointer to the allocated area.
   *
   * @param bytes number of bytes to allocate
   * @return the nex available heap address or -1 if no more space.
   */
  public int allocateBytesFromHeap(int bytes) {
    int address = heap;
    // allocate bytes
    heap += bytes;
    for (int i = 0; i < bytes + Data.offsetToWordAlign(heap); i++) {
      privStoreByte(address + i, (byte) 0);
    }
    // align to a word boundary (if necessary)
    heap = Data.alignToWordBoundary(heap);
    return address;
  }

  /**
   * Returns current heap pointer.
   *
   * @return current heap pointer
   */
  public int getHeapPointer() {
    return heap;
  }

  /**
   * Gets the current diff of the main memory.
   *
   * @return main memory diff
   */
  public HashMap<Integer, Byte> getDiff() {
    HashMap<Integer, Byte> old = diff;
    diff = new HashMap<Integer, Byte>();
    return old;
  }

  /**
   * Checks if the given address is a valid store address.
   *
   * @param address the address to check
   * @param read true = check for reading, false = check for writing
   * @return true if the address is valid, false otherwise.
   */
  private boolean checkAddress(int address, boolean read) {
    // reserved memory ?
    if (Data.inRange(address, Data.RESERVED_LOW_START, Data.RESERVED_LOW_END))
      return false;
    if (Data.inRange(address, Data.RESERVED_HIGH_START, Data.RESERVED_HIGH_END))
      return false;
    // text segment
    if (Data.inRange(address, Data.TEXT, textEnd))
      return false;
    // read only data ?
    return !(!read && Data.inRange(address, rodataStart, rodataEnd));
  }

}
