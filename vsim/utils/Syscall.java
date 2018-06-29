/*
Copyright (C) 2018 Andres Castellanos

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

package vsim.utils;

import vsim.Globals;
import vsim.Settings;
import java.io.IOException;


/**
 * The class Syscall contains methods that implement syscalls such
 * as the SPIM simulator.
 */
public final class Syscall {

  // available syscall codes
  private static final int PRINT_INT    = 1;
  private static final int PRINT_FLOAT  = 2;
  private static final int PRINT_STRING = 4;
  private static final int READ_INT     = 5;
  private static final int READ_FLOAT   = 6;
  private static final int READ_STRING  = 8;
  private static final int SBRK         = 9;
  private static final int EXIT         = 10;
  private static final int PRINT_CHAR   = 11;
  private static final int READ_CHAR    = 12;
  private static final int OPEN         = 13;
  private static final int READ         = 14;
  private static final int WRITE        = 15;
  private static final int CLOSE        = 16;
  private static final int EXIT2        = 17;
  private static final int SLEEP        = 18;

  /**
   * This method is used to simulate the ecall instruction, it is
   * called in {@link vsim.riscv.instructions.itype.Ecall#compute} to handle
   * the ecall request. First obtains the syscall code from register a0 and then
   * tries to match this code with the available syscalls. If the code does not
   * match an available syscall a warning is generated.
   */
  public static void handler() {
    int syscode = Globals.regfile.getRegister("a0");
    // match syscall code
    switch (syscode) {
      case PRINT_INT:
        Syscall.printInt();
        break;
      case PRINT_FLOAT:
        Syscall.printFloat();
        break;
      case PRINT_STRING:
        Syscall.printString();
        break;
      case READ_INT:
        Syscall.readInt();
        break;
      case READ_FLOAT:
        Syscall.readFloat();
        break;
      case READ_STRING:
        Syscall.readString();
        break;
      case SBRK:
        Syscall.sbrk();
        break;
      case EXIT:
        Syscall.exit();
        break;
      case PRINT_CHAR:
        Syscall.printChar();
        break;
      case READ_CHAR:
        Syscall.readChar();
        break;
      case OPEN:
        Syscall.open();
        break;
      case READ:
        Syscall.read();
        break;
      case WRITE:
        Syscall.write();
        break;
      case CLOSE:
        Syscall.close();
        break;
      case EXIT2:
        Syscall.exit2();
        break;
      case SLEEP:
        Syscall.sleep();
        break;
      default:
        if (!Settings.QUIET)
          Message.warning("ecall: invalid syscall code: " + syscode);
    }
  }

  /**
   * This method implements the PRINT_INT syscall, first gets the int
   * value from a1 register and then prints it to stdout.
   */
  private static void printInt() {
    int num = Globals.regfile.getRegister("a1");
    IO.stdout.print(num);
  }

  /**
   * This method implements the PRINT_FLOAT syscall, first gets the float
   * value from f12 register and then prints it to stdout.
   */
  private static void printFloat() {
    float num = Globals.fregfile.getRegister("f12");
    IO.stdout.print(num);
  }

  /**
   * This method implements the PRINT_STRING syscall, first gets the address
   * of the buffer to print from register a1 and then obtains char by char
   * the null terminated string from memory, then prints it.
   */
  private static void printString() {
    int buffer = Globals.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer(0);
    char c;
    while ((c = (char)Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    IO.stdout.print(s);
  }

  /**
   * This method implements the READ_INT syscall, first tries to get the int
   * value from stdin and then saves it in register a0.
   */
  private static void readInt() {
    try {
      Globals.regfile.setRegister("a0", Integer.parseInt(IO.stdin.readLine()));
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: integer number could not be read");
    } catch (NumberFormatException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: invalid integer number");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  /**
   * This method implements the READ_FLOAT syscall, first tries to get the float
   * value from stdin and then saves it in register f0.
   */
  private static void readFloat() {
    try {
      Globals.fregfile.setRegister("f0", Float.parseFloat(IO.stdin.readLine()));
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: float number could not be read");
    } catch (NumberFormatException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: invalid float number");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  /**
   * This method implements the READ_STRING syscall, first tries to read a
   * line from stdin, then stores char by char the string in memory starting
   * at address given in register a1 until length given in register a2 is reached.
   */
  private static void readString() {
    try {
      String s = IO.stdin.readLine();
      int buffer = Globals.regfile.getRegister("a1");
      int length = Globals.regfile.getRegister("a2");
      int minLength = Math.min(length, s.length());
      for (int i = 0; i < minLength; i++) {
        // null terminated string
        if (i == (minLength - 1))
          Globals.memory.storeByte(buffer++, 0);
        else {
          char c = s.charAt(i);
          Globals.memory.storeByte(buffer++, c);
        }
      }
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: string could not be read");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  /**
   * This method implements the SBRK syscall, first gets the number of
   * bytes to allocate from register a1, then calls {@link vsim.riscv.Memory#allocateBytesFromHeap}
   * to obtain the address/pointer to the allocated space then saves the result
   * in register a0.
   */
  private static void sbrk() {
    int numBytes = Globals.regfile.getRegister("a1");
    if (numBytes > 0) {
      int address = Globals.memory.allocateBytesFromHeap(numBytes);
      Globals.regfile.setRegister("a0", address);
    } else {
      if (!Settings.QUIET)
        Message.warning("ecall: number of bytes should be > 0");
    }
  }

  /**
   * This method implements the EXIT syscall, first logs the status, then
   * calls System.exit(0).
   */
  private static void exit() {
    IO.stdout.println();
    Message.log("exit(0)");
    System.exit(0);
  }

  /**
   * This method implements the PRINT_CHAR syscall, first obtains the char
   * value from register a1, then prints it to stdout.
   */
  private static void printChar() {
    char c = (char)Globals.regfile.getRegister("a1");
    IO.stdout.print(c);
  }

  /**
   * This method implements the READ_CHAR syscall, first tries to read a
   * char from stdin, then saves the char value in register a0.
   */
  private static void readChar() {
    try {
      Globals.regfile.setRegister("a0", IO.stdin.read());
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: char could not be read");
    }
  }

  /**
   * This method implements the OPEN syscall.
   *
   * @see vsim.utils.FS#open
   */
  private static void open() {
    int buffer = Globals.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer(0);
    char c;
    while ((c = (char)Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    Globals.regfile.setRegister(
      "a0",
      FS.open(
        s.toString(),
        Globals.regfile.getRegister("a2")
      )
    );
  }

  /**
   * This method implements the READ syscall.
   *
   * @see vsim.utils.FS#read
   */
  private static void read() {
    if (Globals.regfile.getRegister("a3") > 0) {
      Globals.regfile.setRegister(
        "a0",
        FS.read(
          Globals.regfile.getRegister("a1"),
          Globals.regfile.getRegister("a2"),
          Globals.regfile.getRegister("a3")
        )
      );
    } else {
      if (!Settings.QUIET)
        Message.warning("ecall: number of bytes should be > 0");
    }
  }

  /**
   * This method implements the WRITE syscall.
   *
   * @see vsim.utils.FS#write
   */
  private static void write() {
    if (Globals.regfile.getRegister("a3") > 0) {
      Globals.regfile.setRegister(
        "a0",
        FS.write(
          Globals.regfile.getRegister("a1"),
          Globals.regfile.getRegister("a2"),
          Globals.regfile.getRegister("a3")
        )
      );
    } else {
      if (!Settings.QUIET)
        Message.warning("ecall: number of bytes should be > 0");
    }
  }

  /**
   * This method implements the CLOSE syscall.
   *
   * @see vsim.utils.FS#close
   */
  private static void close() {
    Globals.regfile.setRegister(
      "a0",
      FS.close(Globals.regfile.getRegister("a1"))
    );
  }

  /**
   * This method implements the EXIT2 syscall, first obtains the status code
   * from register a1, prints the status, then calls System.exit with the
   * obtained status.
   */
  private static void exit2() {
    int status = Globals.regfile.getRegister("a1");
    IO.stdout.println();
    Message.log("exit(" + status + ")");
    System.exit(status);
  }

  /**
   * This method implements the SLEEP syscall.
   */
  private static void sleep() {
    int millis = Globals.regfile.getRegister("a1");
    if (millis > 0) {
      try {
        Thread.sleep(millis);
      } catch (Exception e) { /* DO NOTHING*/ }
    } else {
      if (!Settings.QUIET)
        Message.warning("ecall: milliseconds should be > 0");
    }
  }

}
