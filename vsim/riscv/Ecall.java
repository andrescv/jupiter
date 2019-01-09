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
import vsim.Globals;
import vsim.Settings;
import vsim.riscv.exceptions.SimulationException;
import vsim.simulator.Status;
import vsim.utils.Data;
import vsim.utils.FS;
import vsim.utils.IO;
import vsim.utils.Message;


/**
 * The class Ecall contains methods that implement common environmental calls.
 */
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
  public static void handler() throws SimulationException {
    // match ecall code
    switch (Globals.regfile.getRegister("a0")) {
      case PRINT_INT:
        Ecall.printInt();
        break;
      case PRINT_FLOAT:
        Ecall.printFloat();
        break;
      case PRINT_STRING:
        Ecall.printString();
        break;
      case READ_INT:
        Ecall.readInt();
        break;
      case READ_FLOAT:
        Ecall.readFloat();
        break;
      case READ_STRING:
        Ecall.readString();
        break;
      case SBRK:
        Ecall.sbrk();
        break;
      case EXIT:
        Ecall.exit();
        break;
      case PRINT_CHAR:
        Ecall.printChar();
        break;
      case READ_CHAR:
        Ecall.readChar();
        break;
      case OPEN:
        Ecall.open();
        break;
      case READ:
        Ecall.read();
        break;
      case WRITE:
        Ecall.write();
        break;
      case CLOSE:
        Ecall.close();
        break;
      case EXIT2:
        Ecall.exit2();
        break;
      case SLEEP:
        Ecall.sleep();
        break;
      case CWD:
        Ecall.cwd();
        break;
      case TIME:
        Ecall.time();
        break;
      case PRINT_HEX:
        Ecall.printHex();
        break;
      case PRINT_BIN:
        Ecall.printBin();
        break;
      case PRINT_USGN:
        Ecall.printUsgn();
        break;
      case SET_SEED:
        Ecall.setSeed();
        break;
      case RAND_INT:
        Ecall.randInt();
        break;
      case RAND_INT_RNG:
        Ecall.randIntRng();
        break;
      case RAND_FLOAT:
        Ecall.randFloat();
        break;
      default:
        if (Settings.EXTRICT)
          throw new SimulationException("ecall: invalid ecall code: " + Globals.regfile.getRegister("a0"));
        else
          Message.warning("ecall: invalid ecall code: " + Globals.regfile.getRegister("a0"));
    }
  }

  /**
   * This method implements the PRINT_INT syscall, first gets the int value from a1 register and then prints it to
   * stdout.
   */
  private static void printInt() {
    int num = Globals.regfile.getRegister("a1");
    IO.stdout.print(Integer.toString(num));
  }

  /**
   * This method implements the PRINT_FLOAT syscall, first gets the float value from f12 register and then prints it to
   * stdout.
   */
  private static void printFloat() {
    float num = Globals.fregfile.getRegister("fa0");
    IO.stdout.print(Float.toString(num));
  }

  /**
   * This method implements the PRINT_STRING syscall, first gets the address of the buffer to print from register a1 and
   * then obtains char by char the null terminated string from memory, then prints it.
   */
  private static void printString() throws SimulationException {
    int buffer = Globals.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer(0);
    char c;
    while ((c = (char) Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    IO.stdout.print(s.toString());
  }

  /**
   * This method implements the READ_INT syscall, first tries to get the int value from stdin and then saves it in
   * register a0.
   */
  private static void readInt() throws SimulationException {
    try {
      Globals.regfile.setRegister("a0", IO.readInt());
    } catch (NumberFormatException e) {
      throw new SimulationException("ecall: invalid integer input");
    }
  }

  /**
   * This method implements the READ_FLOAT syscall, first tries to get the float value from stdin and then saves it in
   * register fa0.
   */
  private static void readFloat() throws SimulationException {
    try {
      Globals.fregfile.setRegister("fa0", IO.readFloat());
    } catch (NumberFormatException e) {
      throw new SimulationException("ecall: invalid float input");
    }
  }

  /**
   * This method implements the READ_STRING syscall, first tries to read a line from stdin, then stores char by char the
   * string in memory starting at address given in register a1 until length given in register a2 is reached.
   */
  private static void readString() throws SimulationException {
    int buffer = Globals.regfile.getRegister("a1");
    int length = Math.max(Globals.regfile.getRegister("a2") - 1, 0);
    String s = IO.readString(length);
    int minLength = Math.min(length, s.length());
    for (int i = 0; i < minLength; i++) {
      char c = s.charAt(i);
      Globals.memory.storeByte(buffer++, c);
    }
    Globals.memory.storeByte(buffer, 0);
  }

  /**
   * This method implements the SBRK syscall, first gets the number of bytes to allocate from register a1, then calls
   * {@link vsim.riscv.Memory#allocateBytesFromHeap} to obtain the address/pointer to the allocated space then saves the
   * result in register a0.
   */
  private static void sbrk() throws SimulationException {
    int numBytes = Globals.regfile.getRegister("a1");
    if (numBytes >= 0) {
      int address = Globals.memory.allocateBytesFromHeap(numBytes);
      Globals.regfile.setRegister("a0", address);
    } else
      throw new SimulationException("ecall: number of bytes should be >= 0");
  }

  /**
   * This method implements the EXIT syscall, first logs the status, then calls System.exit(0).
   */
  private static void exit() {
    IO.stdout.println();
    Message.log("exit(0)");
    Status.EXIT.set(true);
    if (!Settings.GUI)
      System.exit(0);
  }

  /**
   * This method implements the PRINT_CHAR syscall, first obtains the char value from register a1, then prints it to
   * stdout.
   */
  private static void printChar() {
    char c = (char) Globals.regfile.getRegister("a1");
    IO.stdout.print(c + "");
  }

  /**
   * This method implements the READ_CHAR syscall, first tries to read a char from stdin, then saves the char value in
   * register a0.
   */
  private static void readChar() {
    Globals.regfile.setRegister("a0", IO.readChar());
  }

  /**
   * This method implements the OPEN syscall.
   *
   * @see vsim.utils.FS#open
   */
  private static void open() throws SimulationException {
    int buffer = Globals.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer(0);
    char c;
    while ((c = (char) Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    Globals.regfile.setRegister("a0", FS.open(s.toString(), Globals.regfile.getRegister("a2")));
  }

  /**
   * This method implements the READ syscall.
   *
   * @see vsim.utils.FS#read
   */
  private static void read() throws SimulationException {
    if (Globals.regfile.getRegister("a3") > 0) {
      Globals.regfile.setRegister("a0", FS.read(Globals.regfile.getRegister("a1"), Globals.regfile.getRegister("a2"),
          Globals.regfile.getRegister("a3")));
    } else
      throw new SimulationException("ecall: number of bytes should be > 0");
  }

  /**
   * This method implements the WRITE syscall.
   *
   * @see vsim.utils.FS#write
   */
  private static void write() throws SimulationException {
    if (Globals.regfile.getRegister("a3") > 0) {
      Globals.regfile.setRegister("a0", FS.write(Globals.regfile.getRegister("a1"), Globals.regfile.getRegister("a2"),
          Globals.regfile.getRegister("a3")));
    } else
      throw new SimulationException("ecall: number of bytes should be > 0");
  }

  /**
   * This method implements the CLOSE syscall.
   *
   * @see vsim.utils.FS#close
   */
  private static void close() throws SimulationException {
    Globals.regfile.setRegister("a0", FS.close(Globals.regfile.getRegister("a1")));
  }

  /**
   * This method implements the EXIT2 syscall, first obtains the status code from register a1, prints the status, then
   * calls System.exit with the obtained status.
   */
  private static void exit2() {
    int status = Globals.regfile.getRegister("a1");
    Status.EXIT.set(true);
    IO.stdout.println();
    Message.log("exit(" + status + ")");
    if (!Settings.GUI)
      System.exit(status);
  }

  /**
   * This method implements the SLEEP syscall.
   */
  private static void sleep() throws SimulationException {
    int millis = Globals.regfile.getRegister("a1");
    if (millis >= 0) {
      try {
        Thread.sleep(millis);
      } catch (Exception e) {
        /* DO NOTHING */ }
    } else
      throw new SimulationException("ecall: milliseconds should be >= 0");
  }

  /**
   * This method implements the PWD syscall.
   */
  private static void cwd() throws SimulationException {
    String path = System.getProperty("user.dir");
    int buffer = Globals.regfile.getRegister("a1");
    for (int i = 0; i < path.length(); i++) {
      Globals.memory.storeByte(buffer++, (int) path.charAt(i));
    }
    Globals.memory.storeByte(buffer, 0);
  }

  /**
   * This method implements the TIME syscall.
   */
  private static void time() {
    long time = System.currentTimeMillis();
    Globals.regfile.setRegister("a1", (int) (time >>> Data.WORD_LENGTH_BITS));
    Globals.regfile.setRegister("a0", (int) (time & 0xffffffffL));
  }

  /**
   * This method implements the PRINT_HEX syscall.
   */
  private static void printHex() {
    int value = Globals.regfile.getRegister("a1");
    IO.stdout.print(String.format("0x%08x", value));
  }

  /**
   * This method implements the PRINT_BIN syscall.
   */
  private static void printBin() {
    int value = Globals.regfile.getRegister("a1");
    IO.stdout.print(String.format("0b%32s", Integer.toBinaryString(value)).replace(' ', '0'));
  }

  /**
   * This method implements the PRINT_USGN syscall.
   */
  private static void printUsgn() {
    int value = Globals.regfile.getRegister("a1");
    IO.stdout.print(Long.toString(Integer.toUnsignedLong(value)));
  }

  /**
   * This method implements the SET_SEED syscall.
   */
  private static void setSeed() {
    Ecall.RNG.setSeed(Globals.regfile.getRegister("a1"));
  }

  /**
   * This method implements the RAND_INT syscall.
   */
  private static void randInt() {
    Globals.regfile.setRegister("a0", Ecall.RNG.nextInt());
  }

  /**
   * This method implements the RAND_INT_RNG syscall.
   */
  private static void randIntRng() {
    int min = Globals.regfile.getRegister("a1");
    int max = Globals.regfile.getRegister("a2");
    Globals.regfile.setRegister("a0", Ecall.RNG.nextInt((max - min) + 1) + min);
  }

  /**
   * This method implements the RAND_FLOAT syscall.
   */
  private static void randFloat() {
    Globals.fregfile.setRegister("fa0", Ecall.RNG.nextFloat());
  }

}
