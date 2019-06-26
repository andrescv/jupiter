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

import java.util.Random;

import vsim.State;
import vsim.exceptions.*;
import vsim.utils.Data;
import vsim.utils.IO;


/** Contains methods that implement common environmental calls. */
public final class Ecall {

  // SPIM ecall codes
  private static final int PRINT_INT = 1;
  private static final int PRINT_FLOAT = 2;
  private static final int PRINT_STRING = 4;
  private static final int READ_INT = 5;
  private static final int READ_FLOAT = 6;
  private static final int READ_STRING = 8;
  private static final int SBRK = 9;
  private static final int EXIT = 10;
  private static final int PRINT_CHAR = 11;
  private static final int READ_CHAR = 12;
  private static final int OPEN = 13;
  private static final int READ = 14;
  private static final int WRITE = 15;
  private static final int CLOSE = 16;
  private static final int EXIT2 = 17;
  // V-Sim ecall codes
  private static final int SLEEP = 18;
  private static final int CWD = 19;
  private static final int TIME = 20;
  private static final int PRINT_HEX = 21;
  private static final int PRINT_BIN = 22;
  private static final int PRINT_USGN = 23;
  private static final int SET_SEED = 24;
  private static final int RAND_INT = 25;
  private static final int RAND_INT_RNG = 26;
  private static final int RAND_FLOAT = 27;

  /** random number generator */
  private static final Random RNG = new Random();

  /**
   * This method is used to simulate the ecall instruction, it is called in
   * {@link vsim.riscv.instructions.itype.Ecall#compute} to handle the ecall request. First obtains the syscall code
   * from register a0 and then tries to match this code with the available syscalls. If the code does not match an
   * available syscall a warning is generated.
   *
   * @throws SimulationException if some exception occur while handling environmental call
   */
  public static void handler(State state) throws SimulationException {
    // match ecall code
    switch (state.xregfile().getRegister("a0")) {
      case PRINT_INT:
        printInt(state);
        break;
      case PRINT_FLOAT:
        printFloat(state);
        break;
      case PRINT_STRING:
        printString(state);
        break;
      case READ_INT:
        readInt(state);
        break;
      case READ_FLOAT:
        readFloat(state);
        break;
      case READ_STRING:
        readString(state);
        break;
      case SBRK:
        sbrk(state);
        break;
      case EXIT:
        exit();
        break;
      case PRINT_CHAR:
        printChar(state);
        break;
      case READ_CHAR:
        readChar(state);
        break;
      case OPEN:
        open(state);
        break;
      case READ:
        read(state);
        break;
      case WRITE:
        write(state);
        break;
      case CLOSE:
        close(state);
        break;
      case EXIT2:
        exit2(state);
        break;
      case SLEEP:
        sleep(state);
        break;
      case CWD:
        cwd(state);
        break;
      case TIME:
        time(state);
        break;
      case PRINT_HEX:
        printHex(state);
        break;
      case PRINT_BIN:
        printBin(state);
        break;
      case PRINT_USGN:
        printUsgn(state);
        break;
      case SET_SEED:
        setSeed(state);
        break;
      case RAND_INT:
        randInt(state);
        break;
      case RAND_INT_RNG:
        randIntRng(state);
        break;
      case RAND_FLOAT:
        randFloat(state);
        break;
      default:
        throw new SimulationException("invalid ecall code: " + state.xregfile().getRegister("a0"));
    }
  }

  /** Implements the PRINT_INT ecall. */
  private static void printInt(State state) {
    int num = state.xregfile().getRegister("a1");
    IO.stdout().print(Integer.toString(num));
  }

  /** Implements the PRINT_FLOAT ecall. */
  private static void printFloat(State state) {
    float num = state.fregfile().getRegister("fa0");
    IO.stdout().print(Float.toString(num));
  }

  /** Implements the PRINT_STRING ecall. */
  private static void printString(State state) throws SimulationException {
    int buffer = state.xregfile().getRegister("a1");
    // 65684
    StringBuilder s = new StringBuilder(0);
    char c;
    while ((c = (char) state.memory().loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    IO.stdout().print(s.toString());
  }

  /** Implements the READ_INT ecall. */
  private static void readInt(State state) throws SimulationException {
    try {
      state.xregfile().setRegister("a0", IO.readInt());
    } catch (NumberFormatException e) {
      throw new SimulationException("ecall: the input is not a valid integer");
    }
  }

  /** Implements the READ_FLOAT ecall. */
  private static void readFloat(State state) throws SimulationException {
    try {
      state.fregfile().setRegister("fa0", IO.readFloat());
    } catch (NumberFormatException e) {
      throw new SimulationException("ecall: the input is not a valid float");
    }
  }

  /** Implements the READ_STRING ecall. */
  private static void readString(State state) throws SimulationException {
    int buffer = state.xregfile().getRegister("a1");
    int length = Math.max(state.xregfile().getRegister("a2") - 1, 0);
    String s = IO.readString(length);
    int minLength = Math.min(length, s.length());
    for (int i = 0; i < minLength; i++) {
      char c = s.charAt(i);
      state.memory().storeByte(buffer++, c);
    }
    state.memory().storeByte(buffer, 0);
  }

  /** Implements SBRK ecall. */
  private static void sbrk(State state) throws SimulationException {
    int numBytes = state.xregfile().getRegister("a1");
    if (numBytes >= 0) {
      int address = state.memory().allocateBytesFromHeap(numBytes);
      state.xregfile().setRegister("a0", address);
    } else {
      throw new SimulationException("ecall: number of bytes should be >= 0");
    }
  }

  /** Implements exit(0) ecall. */
  private static void exit() throws SimulationException {
    throw new HaltException(0);
  }

  /** Implements PRINT_CHAR ecall. */
  private static void printChar(State state) {
    IO.stdout().print((char) state.xregfile().getRegister("a1") + "");
  }

  /** Implements READ_CHAR ecall. */
  private static void readChar(State state) {
    state.xregfile().setRegister("a0", IO.readChar());
  }

  /** Implements OPEN ecall. */
  private static void open(State state) {

  }

  /** Implements READ ecall. */
  private static void read(State state) {

  }

  /** Implements WRITE ecall. */
  private static void write(State state) {

  }

  /** Implements CLOSE ecall. */
  private static void close(State state) {

  }

  /** Implements EXIT2 ecall. */
  private static void exit2(State state) throws SimulationException {
    throw new HaltException(state.xregfile().getRegister("a1"));
  }

  /** Implements SLEEP ecall. */
  private static void sleep(State state) throws SimulationException {
    int millis = state.xregfile().getRegister("a1");
    if (millis >= 0) {
      try {
        Thread.sleep(millis);
      } catch (Exception e) { /* DO NOTHING */ }
    } else {
      throw new SimulationException("ecall: milliseconds should be >= 0");
    }
  }

  /** Implements CWD ecall. */
  private static void cwd(State state) throws SimulationException {
    String path = System.getProperty("user.dir");
    int buffer = state.xregfile().getRegister("a1");
    for (int i = 0; i < path.length(); i++) {
      state.memory().storeByte(buffer++, (int) path.charAt(i));
    }
    state.memory().storeByte(buffer, 0);
  }

  /** Implements TIME ecall. */
  private static void time(State state) {
    long time = System.currentTimeMillis();
    state.xregfile().setRegister("a1", (int) (time >>> Data.WORD_LENGTH_BITS));
    state.xregfile().setRegister("a0", (int) (time & 0xffffffffL));
  }

  /** Implements PRINT_HEX ecall. */
  private static void printHex(State state) {
    IO.stdout().print(String.format("0x%08x", state.xregfile().getRegister("a1")));
  }

  /** Implements the PRINT_BIN syscall. */
  private static void printBin(State state) {
    IO.stdout().print(String.format("0b%32s", Integer.toBinaryString(state.xregfile().getRegister("a1"))).replace(' ', '0'));
  }

  /** Implements the PRINT_USGN syscall. */
  private static void printUsgn(State state) {
    IO.stdout().print(Long.toString(Integer.toUnsignedLong(state.xregfile().getRegister("a1"))));
  }

  /** Implements the SET_SEED syscall. */
  private static void setSeed(State state) {
    RNG.setSeed(state.xregfile().getRegister("a1"));
  }

  /** Implements the RAND_INT syscall. */
  private static void randInt(State state) {
    state.xregfile().setRegister("a0", RNG.nextInt());
  }

  /** Implements the RAND_INT_RNG syscall. */
  private static void randIntRng(State state) {
    int min = state.xregfile().getRegister("a1");
    int max = state.xregfile().getRegister("a2");
    state.xregfile().setRegister("a0", RNG.nextInt((max - min) + 1) + min);
  }

  /** Implements the RAND_FLOAT syscall. */
  private static void randFloat(State state) {
    state.fregfile().setRegister("fa0", RNG.nextFloat());
  }

}
