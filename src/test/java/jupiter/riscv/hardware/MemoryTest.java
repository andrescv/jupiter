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

package jupiter.riscv.hardware;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import jupiter.exc.InvalidAddressException;
import jupiter.utils.Data;


/** jupiter.riscv.hardware.Memory tests. */
public class MemoryTest {

  private static int textEnd = Data.TEXT + 20 * Data.WORD_LENGTH - 1;
  private static int rodataStart = textEnd + 1;
  private static int rodataEnd = rodataStart + 15;
  private static int heapStart = rodataStart + 32;

  @Test
  void testObserver() throws Exception {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    Observer observer = new Observer();
    memory.addObserver(observer);
    memory.storeByte(rodataEnd + 1, 0x1);
    assertEquals("here", observer.message);
    memory.removeObserver(observer);
    memory.storeByte(rodataEnd + 1, 0x2);
    assertEquals("here", observer.message);
  }

  @Test
  void testReset() throws InvalidAddressException {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    memory.storeWord(rodataEnd + 1, 0x00ff00ff);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH, 0xff00ff00);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 2, 0x0ff00ff0);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 3, 0xf00ff00f);
    assertEquals(0x00ff00ff, memory.loadWord(rodataEnd + 1));
    assertEquals(0xff00ff00, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x0ff00ff0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0xf00ff00f, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    memory.reset();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    assertEquals(0x0, memory.loadWord(rodataEnd + 1));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 3));
  }

  @Test
  void testPrint() {
    Memory memory = new Memory();
    memory.print(0x3, 1);
  }

  @Test
  void testHistory() throws InvalidAddressException {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    memory.storeWord(rodataEnd + 1, 0x00ff00ff);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH, 0xff00ff00);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 2, 0x0ff00ff0);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 3, 0xf00ff00f);
    assertEquals(0x00ff00ff, memory.loadWord(rodataEnd + 1));
    assertEquals(0xff00ff00, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x0ff00ff0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0xf00ff00f, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    HashMap<Integer, Byte> diff = memory.getDiff();
    assertEquals(16, diff.size());
    memory.restore(diff);
    assertEquals(0x0, memory.loadWord(rodataEnd + 1));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0x0, memory.loadWord(rodataEnd + 1 + Data.WORD_LENGTH * 3));
  }

  @Test
  void testPrivLoads() throws InvalidAddressException {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    memory.storeWord(rodataEnd + 1, 0x00ff00ff);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH, 0xff00ff00);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 2, 0x0ff00ff0);
    memory.storeWord(rodataEnd + 1 + Data.WORD_LENGTH * 3, 0xf00ff00f);
    assertEquals(0x00ff00ff, memory.privLoadWord(rodataEnd + 1));
    assertEquals(0xff00ff00, memory.privLoadWord(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x0ff00ff0, memory.privLoadWord(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0xf00ff00f, memory.privLoadWord(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    assertEquals(0x000000ff, memory.privLoadHalf(rodataEnd + 1));
    assertEquals(0xffffff00, memory.privLoadHalf(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x00000ff0, memory.privLoadHalf(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0xfffff00f, memory.privLoadHalf(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    assertEquals(0x000000ff, memory.privLoadHalfUnsigned(rodataEnd + 1));
    assertEquals(0x0000ff00, memory.privLoadHalfUnsigned(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x00000ff0, memory.privLoadHalfUnsigned(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0x0000f00f, memory.privLoadHalfUnsigned(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    assertEquals(0xffffffff, memory.privLoadByte(rodataEnd + 1));
    assertEquals(0x00000000, memory.privLoadByte(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0xfffffff0, memory.privLoadByte(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0x0000000f, memory.privLoadByte(rodataEnd + 1 + Data.WORD_LENGTH * 3));
    assertEquals(0x000000ff, memory.privLoadByteUnsigned(rodataEnd + 1));
    assertEquals(0x00000000, memory.privLoadByteUnsigned(rodataEnd + 1 + Data.WORD_LENGTH));
    assertEquals(0x00000000, memory.privLoadByteUnsigned(heapStart + 1 + Data.WORD_LENGTH));
    assertEquals(0x000000f0, memory.privLoadByteUnsigned(rodataEnd + 1 + Data.WORD_LENGTH * 2));
    assertEquals(0x0000000f, memory.privLoadByteUnsigned(rodataEnd + 1 + Data.WORD_LENGTH * 3));
  }

  @Test
  void tesInvalidtLoads() {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    assertThrows(InvalidAddressException.class, () -> { memory.loadByte(0x0); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadByteUnsigned(0x0); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalf(0x0); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalfUnsigned(0x0); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadWord(0x0); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadByte(0xffffffff); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadByteUnsigned(0xffffffff); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalf(0xffffffff); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalfUnsigned(0xffffffff); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadWord(0xffffffff); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadByte(Data.TEXT); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadByteUnsigned(Data.TEXT); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalf(Data.TEXT); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadHalfUnsigned(Data.TEXT); });
    assertThrows(InvalidAddressException.class, () -> { memory.loadWord(Data.TEXT); });
  }

  @Test
  void testInvalidStores() {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    assertThrows(InvalidAddressException.class, () -> { memory.storeByte(rodataStart, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeByte(0x0, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeByte(0xffffffff, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeByte(Data.TEXT, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeHalf(rodataStart, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeHalf(0x0, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeHalf(0xffffffff, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeHalf(Data.TEXT, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeWord(rodataStart, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeWord(0x0, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeWord(0xffffffff, 1); });
    assertThrows(InvalidAddressException.class, () -> { memory.storeWord(Data.TEXT, 1); });
  }

  @Test
  void testSegments() {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    assertEquals(memory.getHeapSegment(), heapStart);
    assertEquals(memory.getStaticSegment(), rodataStart);
  }

  @Test
  void testAllocateBytesFromHeap() {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    int addr = memory.allocateBytesFromHeap(104);
    assertEquals(heapStart, addr);
    assertEquals(memory.getHeapPointer(), Data.alignToWordBoundary(heapStart + 104));
    addr = memory.allocateBytesFromHeap(20);
    assertEquals(Data.alignToWordBoundary(heapStart + 104), addr);
    assertEquals(memory.getHeapPointer(), Data.alignToWordBoundary(heapStart + 124));
  }

  @Test
  void testHeapPointer() {
    Memory memory = new Memory();
    memory.setLayout(textEnd, rodataStart, rodataEnd, heapStart, true, true);
    memory.setHeapPointer(Data.GLOBAL_POINTER);
    assertEquals(Data.GLOBAL_POINTER, memory.getHeapPointer());
  }

}
